package com.java.urlShortener.infra.dataAccess;

import java.util.HashMap;

import com.java.urlShortener.domain.ShortUrl;

public class UrlMemoryStorage implements IUrlStorage {

	private HashMap<String, String> shortUrlMap;

	public UrlMemoryStorage() {
		shortUrlMap = new HashMap<String, String>();
	}

	@Override
	public void addShortUrl(ShortUrl shortUrl) throws Exception {

		if (shortUrlMap.containsKey(shortUrl.toString())) {
			if (shortUrlMap.get(shortUrl.toString()).equals(shortUrl.getOriginalUrl()))
				return;
			else
				throw new Exception("Duplicated Key");
		}

		shortUrlMap.put(shortUrl.getShortUrl(), shortUrl.getOriginalUrl());
	}

	@Override
	public ShortUrl getShortUrl(String shortUrl) {

		if (!shortUrlMap.containsKey(shortUrl))
			return null;

		String originalUrl = shortUrlMap.get(shortUrl);

		return new ShortUrl(shortUrl, originalUrl);
	}
}