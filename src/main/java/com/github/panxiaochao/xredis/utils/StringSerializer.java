package com.github.panxiaochao.xredis.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.SerializationException;

public class StringSerializer {
	/**
	 * Refer to
	 * https://docs.oracle.com/javase/8/docs/technotes/guides/intl/encoding.doc.html
	 * UTF-8, UTF-16, UTF-32, ISO-8859-1, GBK, Big5, etc
	 */
	private static final String DEFAULT_CHARSET = "UTF-8";

	public static byte[] serialize(String s) throws SerializationException {
		try {
			return (s == null ? null : s.getBytes(DEFAULT_CHARSET));
		} catch (UnsupportedEncodingException e) {
			throw new SerializationException("serialize error, string=" + s, e);
		}
	}

	public static String deserialize(byte[] bytes) throws SerializationException {
		try {
			return (bytes == null ? null : new String(bytes, DEFAULT_CHARSET));
		} catch (UnsupportedEncodingException e) {
			throw new SerializationException("deserialize error", e);
		}
	}
}
