package com.github.panxiaochao.xredis.utils;

import java.io.Closeable;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtils {
	private static final Logger log = Logger.getLogger(PropertiesUtils.class);
	private static final Properties PROPERTIES_MAPPING = new Properties();
	private static final String PROPERTIES_FILE = "pxc-redis.properties";

	static {
		try {
			ClassLoader ctxClassLoader = ClassUtils.getClassLoader();
			if (ctxClassLoader != null) {
				ArrayList<String> properties_file_list = new ArrayList<String>();
				Enumeration<URL> e = ctxClassLoader.getResources(PROPERTIES_FILE);
				while (e.hasMoreElements()) {
					URL url = e.nextElement();
					properties_file_list.add(url.toString());
				}

				for (int i = properties_file_list.size(); i > 0; i--) {
					URL url = new URL(properties_file_list.get(i - 1));
					Properties property = new Properties();
					InputStream is = null;
					try {
						is = url.openStream();
						property.load(is);
					} finally {
						close(is);
					}
					PROPERTIES_MAPPING.putAll(property);
				}
				// showKeysAndValues(PROPERTIES_MAPPING);
			}
		} catch (Exception e) {
			log.error("load pxc-redis.properties error", e);
		}

	}

	private static void showKeysAndValues(Properties properties) {
		Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();
		System.out.println("Redis配置属性：");
		System.out.println("*************************");
		while (it.hasNext()) {
			Entry<Object, Object> entry = it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key    :" + key);
			System.out.println("value  :" + value);
			System.out.println("--------------------------");
		}
	}

	public static Boolean getBoolean(String key) {
		String property = PROPERTIES_MAPPING.getProperty(key);
		if ("true".equals(property)) {
			return Boolean.TRUE;
		} else if ("false".equals(property)) {
			return Boolean.FALSE;
		}
		return null;
	}

	public static Boolean getBoolean(Properties properties, String key) {
		String property = properties.getProperty(key);
		if ("true".equals(property)) {
			return Boolean.TRUE;
		} else if ("false".equals(property)) {
			return Boolean.FALSE;
		}
		return null;
	}

	public static Integer getInteger(String key) {
		String property = PROPERTIES_MAPPING.getProperty(key);
		if (property == null) {
			return null;
		}
		try {
			return Integer.parseInt(property);
		} catch (NumberFormatException ex) {
			// skip
		}
		return null;
	}

	public static Integer getInteger(Properties properties, String key) {
		String property = properties.getProperty(key);
		if (property == null) {
			return null;
		}
		try {
			return Integer.parseInt(property);
		} catch (NumberFormatException ex) {
			// skip
		}
		return null;
	}

	public static Long getLong(String key) {
		String property = PROPERTIES_MAPPING.getProperty(key);
		if (property == null) {
			return null;
		}
		try {
			return Long.parseLong(property);
		} catch (NumberFormatException ex) {
			// skip
		}
		return null;
	}

	public static Long getLong(Properties properties, String key) {
		String property = properties.getProperty(key);
		if (property == null) {
			return null;
		}
		try {
			return Long.parseLong(property);
		} catch (NumberFormatException ex) {
			// skip
		}
		return null;
	}

	public static String getString(String key) {
		String property = PROPERTIES_MAPPING.getProperty(key);
		if (property == null) {
			return null;
		}
		return property;
	}

	public static String getString(Properties properties, String key) {
		String property = properties.getProperty(key);
		if (property == null) {
			return null;
		}
		return property;
	}

	private static void close(Closeable x) {
		if (x == null) {
			return;
		}

		try {
			x.close();
		} catch (Exception e) {
			log.error("close error", e);
		}
	}
}
