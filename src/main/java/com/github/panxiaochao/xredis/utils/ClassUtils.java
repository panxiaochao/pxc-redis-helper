package com.github.panxiaochao.xredis.utils;

public class ClassUtils {
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
}
