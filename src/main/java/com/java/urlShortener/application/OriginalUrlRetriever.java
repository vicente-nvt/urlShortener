package com.java.urlShortener.application;

import com.java.urlShortener.domain.ShortUrlRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("originalUrlRetriever")
public class OriginalUrlRetriever {

    private ShortUrlRepository shortUrlRepository;

    @Autowired
	public OriginalUrlRetriever(ShortUrlRepository shortUrlRepository) {
		this.shortUrlRepository = shortUrlRepository;
    }

	public String getOriginalUrl(String urlKey) throws Exception {
		return shortUrlRepository.getOriginalUrlBy(urlKey);
	}
}