package com.github.panxiaochao.xredis.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panxiaochao.xredis.service.impl.JRedisServiceImpl;

public class SerializeUtils {
	private static final Logger log = LoggerFactory.getLogger(SerializeUtils.class);

	public static byte[] serialize(Object object) {
		byte[] result = null;
		if (object == null) {
			return new byte[0];
		}
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
			try {
				if (!(object instanceof Serializable)) {
					throw new IllegalArgumentException(
							SerializeUtils.class.getSimpleName() + " requires a Serializable payload "
									+ "but received an object of type [" + object.getClass().getName() + "]");
				}
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
				objectOutputStream.writeObject(object);
				objectOutputStream.flush();
				result = byteStream.toByteArray();
			} catch (Throwable ex) {
				log.error("Failed to serialize", ex);
			}
		} catch (Exception ex) {
			log.error("Failed to serialize", ex);
		}
		return result;
	}

	public static Object unserialize(byte[] bytes) {
		Object result = null;
		if (isEmpty(bytes)) {
			return null;
		}
		try {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
				try {
					result = objectInputStream.readObject();
				} catch (ClassNotFoundException ex) {
					throw new Exception("Failed to deserialize object type", ex);
				}
			} catch (Throwable ex) {
				throw new Exception("Failed to deserialize", ex);
			}
		} catch (Exception e) {
			log.error("Failed to deserialize", e);
		}
		return result;
	}

	private static boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}
}
