package com.java.urlShortener.domain;

import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class KeyGenerator {
	public static String generateKey(String url) {
		byte bytes[] = url.getBytes();
		Checksum checksum = new Adler32();
		checksum.update(bytes, 0, bytes.length);
		long lngChecksum = checksum.getValue();
		return Long.toHexString(lngChecksum);
	}
}
