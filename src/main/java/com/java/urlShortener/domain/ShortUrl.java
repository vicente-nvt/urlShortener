package com.java.urlShortener.domain;

import java.net.URL;
import java.util.regex.Pattern;

public class ShortUrl {

	private String originalUrl;
	private String key;

	public ShortUrl(String originalUrl) throws DomainException {
		this(originalUrl, "");
	}

	public ShortUrl(String originalUrl, String urlKey) throws DomainException {
		boolean urlIsValid = validateUrl(originalUrl);

		if (!urlIsValid)
			throw new DomainException("Invalid URL");

		setKey(originalUrl, urlKey);
		this.originalUrl = originalUrl;
	}

	private void setKey(String originalUrl, String shortUrlKey) {
		this.key = shortUrlKey.isEmpty() ? KeyGenerator.generateKey(originalUrl) : shortUrlKey;
	}

	public String getKey() {
		return this.key;
	}

	public String getOriginalUrl() {
		return this.originalUrl;
	}

	private boolean validateUrl(String url) {
		String patternString = "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$";
		Pattern pattern = Pattern.compile(patternString);

		boolean matchesPattern = pattern.matcher(url).matches();
		boolean canCreateAnURL;

		try {
			new URL(url);
			canCreateAnURL = true;
		} catch (Exception e) {
			canCreateAnURL = false;
		}

		return matchesPattern || canCreateAnURL;
	}
}
