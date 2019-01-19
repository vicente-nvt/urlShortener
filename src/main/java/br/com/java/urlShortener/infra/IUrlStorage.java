package br.com.java.urlShortener.infra;

import br.com.java.urlShortener.domain.ShortUrl;

public interface IUrlStorage {
	void addShortUrl(ShortUrl shortUrl) throws Exception;	
	ShortUrl getShortUrl(String shortUrl);
}
