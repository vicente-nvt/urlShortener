package br.com.java.urlShortener.infra;

import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class Adler32Generator implements IShortUrlGenerator {

	@Override
	public String generateShortUrl(String url) {
		byte bytes[] = url.getBytes();
		Checksum checksum = new Adler32();
		checksum.update(bytes, 0, bytes.length);
		long lngChecksum = checksum.getValue();
		return Long.toHexString(lngChecksum);
	}
}
