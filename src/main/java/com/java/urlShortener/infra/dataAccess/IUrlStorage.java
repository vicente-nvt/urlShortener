package com.java.urlShortener.infra.dataAccess;

import com.java.urlShortener.domain.ShortUrl;

public interface IUrlStorage {
	void addShortUrl(ShortUrl shortUrl) throws Exception;

	ShortUrl getShortUrl(String shortUrl);
}
