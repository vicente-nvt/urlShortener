package br.com.java.urlShortener.service;

import java.net.URL;
import java.util.regex.Pattern;

import br.com.java.urlShortener.domain.ShortUrl;
import br.com.java.urlShortener.infra.IShortUrlGenerator;
import br.com.java.urlShortener.infra.IUrlStorage;

public class UrlShortener implements IUrlShortener {

	private IUrlStorage urlStorage;
	private IShortUrlGenerator shortUrlGenerator;

	public UrlShortener(IUrlStorage urlStorage, IShortUrlGenerator shortUrlGenerator) {
		this.urlStorage = urlStorage;
		this.shortUrlGenerator = shortUrlGenerator;
	}

	public ShortUrl makeShortUrl(String originalUrl) throws Exception {
		validateUrl(originalUrl);
		String shortUrl = shortUrlGenerator.generateShortUrl(originalUrl);
		ShortUrl createdShortUrl = new ShortUrl(shortUrl, originalUrl);
		this.urlStorage.addShortUrl(createdShortUrl);

		return createdShortUrl;
	}

	public ShortUrl getOriginalUrl(String shortUrl) {
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
