package com.java.urlShortener.application;

import com.java.urlShortener.domain.ShortUrl;
import com.java.urlShortener.domain.ShortUrlRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("shortUrlCreator")
public class ShortUrlCreator {

	private ShortUrlRepository shortUrlRepository;

	@Autowired
	public ShortUrlCreator(ShortUrlRepository shortUrlRepository) {
		this.shortUrlRepository = shortUrlRepository;
	}

	public ShortUrl create(String originalUrl) throws Exception {
		
		ShortUrl shortUrl = new ShortUrl(originalUrl);

		this.shortUrlRepository.addShortUrl(shortUrl);

		return shortUrl;
	}
}
