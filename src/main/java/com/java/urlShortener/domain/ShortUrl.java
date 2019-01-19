package com.java.urlShortener.domain;

public class ShortUrl {

	private String originalUrl;
	private String shortUrl;

	public ShortUrl(String shortUrl, String originalUrl) {
		this.shortUrl = shortUrl;
		this.originalUrl = originalUrl;
	}
	
	public String getShortUrl() {
		return this.shortUrl;
	}
	
	public String getOriginalUrl(){
		return this.originalUrl;
	}
}
