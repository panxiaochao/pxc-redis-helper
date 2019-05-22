package com.github.panxiaochao.xredis.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @Title SerializeUtils.java
 * @Description TODO(序列化工具)
 * @Author Lypxc
 * @Date 2018年10月20日
 * @Version 1.0
 */
public class SerializeUtils {
	/**
	 * 
	 * @Description （序列化）
	 * @param object
	 * @return
	 * @date 2018年10月20日
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			oos.close();
			baos.close();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Description （反序列化）
	 * @param bytes
	 * @return
	 * @date 2018年10月20日
	 */
	public static Object unserialize(byte[] bytes) {
		Object obj = null;
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;

		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			obj = ois.readObject();
			ois.close();
			bais.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
