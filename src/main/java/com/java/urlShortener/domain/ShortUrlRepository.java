package com.java.urlShortener.domain;

public interface ShortUrlRepository {
	void addShortUrl(ShortUrl shortUrl) throws Exception;

	String getOriginalUrlBy(String urlKey) throws Exception;
}
