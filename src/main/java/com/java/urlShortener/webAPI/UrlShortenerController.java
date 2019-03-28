package com.java.urlShortener.webAPI;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.urlShortener.application.OriginalUrlRetriever;
import com.java.urlShortener.application.ShortUrlCreator;
import com.java.urlShortener.domain.ShortUrl;
import com.java.urlShortener.infra.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenerController {

	private ShortUrlCreator shortUrlCreator;
	private OriginalUrlRetriever originalUrlRetriever;

	@Autowired
	public UrlShortenerController(ShortUrlCreator shortUrlCreator, OriginalUrlRetriever originalUrlRetriever) {
		this.shortUrlCreator = shortUrlCreator;
		this.originalUrlRetriever = originalUrlRetriever;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> index() {
		return ResponseEntity.ok("URL Shortener");
	}

	@RequestMapping(value = "/shortify", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> createShortUrl(@RequestBody String requestBody) {

		try {
			String urlToShorten = getUrlToShortenFromRequestBody(requestBody);
			ShortUrl shortUrl = shortUrlCreator.create(urlToShorten);

			return new ResponseEntity<Object>(shortUrl, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ObjectError("ShortUrl", e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{url}", method = RequestMethod.GET)
	public void goToOriginalUrl(@PathVariable("url") String shortUrl, HttpServletResponse response) throws IOException {

		try {
			String originalUrl = originalUrlRetriever.getOriginalUrl(shortUrl);

			if (!originalUrl.isEmpty()) {
				response.addHeader("Location", originalUrl);
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			} else
				throw new NotFoundException();

		} catch (NotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	private String getUrlToShortenFromRequestBody(String url) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(url);
		String urlToShortify = actualObj.get("url").asText();
		return urlToShortify;
	}
}