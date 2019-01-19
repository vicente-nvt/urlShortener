package com.java.urlShortener.application;

import com.java.urlShortener.domain.ShortUrl;

public interface IUrlShortener {
	ShortUrl makeShortUrl(String originalUrl) throws Exception;
	ShortUrl getShortUrl(String shortUrl);
}
