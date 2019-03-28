package com.java.urlShortener.infra.dataAccess;

import java.util.HashMap;

import com.java.urlShortener.domain.ShortUrl;
import com.java.urlShortener.domain.ShortUrlRepository;
import com.java.urlShortener.infra.NotFoundException;

public class ShortUrlMemoryDao implements ShortUrlRepository {

	private HashMap<String, String> shortUrlMap;

	public ShortUrlMemoryDao() {
		shortUrlMap = new HashMap<String, String>();
	}

	@Override
	public void addShortUrl(ShortUrl shortUrl) throws Exception {

		boolean isAlreadyMapped = shortUrlMap.containsKey(shortUrl.getKey());

		if (isAlreadyMapped) {
			String mappedOriginalUrl = shortUrlMap.get(shortUrl.getKey());
			String incomingOriginalUrl = shortUrl.getOriginalUrl();
			boolean originalUrlsAreTheSame = mappedOriginalUrl.equals(incomingOriginalUrl);

			if (originalUrlsAreTheSame)
				throw new Exception("Duplicated Key");

			return;
		}

		shortUrlMap.put(shortUrl.getKey(), shortUrl.getOriginalUrl());
	}

	@Override
	public String getOriginalUrlBy(String urlKey) throws NotFoundException {
		if (!shortUrlMap.containsKey(urlKey))
			throw new NotFoundException();

		return shortUrlMap.get(urlKey);
	}
}