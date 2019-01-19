package br.com.java.urlShortener.application;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.java.urlShortener.domain.ShortUrl;
import br.com.java.urlShortener.infra.Adler32Generator;
import br.com.java.urlShortener.infra.UrlStorage;
import br.com.java.urlShortener.service.IUrlShortener;
import br.com.java.urlShortener.service.UrlShortener;

@RestController
public class UrlShortenerController {
	
	IUrlShortener urlShortener;
	
	public UrlShortenerController() {
		UrlStorage urlStorage = new UrlStorage();
		Adler32Generator shortUrlGenerator = new Adler32Generator();
		urlShortener = new UrlShortener(urlStorage, shortUrlGenerator);
	}
	
    @RequestMapping(value="/", method=RequestMethod.GET)
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("URL Shortener");
    }
	
	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> createShortUrl(@RequestBody String url){

		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode actualObj = mapper.readTree(url);	 
			String urlToShortify = actualObj.get("url").asText();			
			ShortUrl shortUrl = urlShortener.makeShortUrl(urlToShortify);
			
			return new ResponseEntity<Object>(shortUrl, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ObjectError("ShortUrl", e.getMessage()), HttpStatus.BAD_REQUEST);
		}				
	}
	
	@RequestMapping(value = "/{url}", method = RequestMethod.GET, produces = "application/json")
	public void goToOriginalUrl(@PathVariable("url") String url, HttpServletResponse response) throws IOException {

			ShortUrl storedUrl = urlShortener.getOriginalUrl(url);			
		    
			if (storedUrl != null){
				response.addHeader("Location", storedUrl.getOriginalUrl());			
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			}
			else
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}