package br.com.java.urlShortener.service;

import br.com.java.urlShortener.domain.ShortUrl;

public interface IUrlShortener {
	ShortUrl makeShortUrl(String originalUrl) throws Exception;
	ShortUrl getOriginalUrl(String shortUrl);
}
