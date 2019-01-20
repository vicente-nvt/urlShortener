package com.java.urlShortener.application;

import java.net.URL;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.urlShortener.domain.ShortUrl;
import com.java.urlShortener.infra.IShortUrlGenerator;
import com.java.urlShortener.infra.dataAccess.IUrlStorage;

@Component("urlShortener")
public class UrlShortener implements IUrlShortener {

	@Autowired
	private IUrlStorage urlStorage;
	@Autowired
	private IShortUrlGenerator shortUrlGenerator;

	public ShortUrl makeShortUrl(String originalUrl) throws Exception {
		validateUrl(originalUrl);
		String generatedShortUrl = shortUrlGenerator.generateShortUrl(originalUrl);
		ShortUrl shortUrl = new ShortUrl(generatedShortUrl, originalUrl);

		this.urlStorage.addShortUrl(shortUrl);

		return shortUrl;
	}

	public ShortUrl getShortUrl(String shortUrl) {
		return this.urlStorage.getShortUrl(shortUrl);
	}

	private void validateUrl(String url) throws Exception {

		Pattern pattern = Pattern
				.compile("^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$");

		boolean matchesPattern = pattern.matcher(url).matches();
		boolean canCreateAnURL;

		try {
			new URL(url);
			canCreateAnURL = true;
		} catch (Exception e) {
			canCreateAnURL = false;
		}

		if (!matchesPattern && !canCreateAnURL)
			throw new Exception("An invalid URL was informed: " + url);
	}
}
