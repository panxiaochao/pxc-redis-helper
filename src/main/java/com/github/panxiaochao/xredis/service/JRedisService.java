package com.github.panxiaochao.xredis.service;

import java.util.List;
import java.util.Map;

public interface JRedisService {

	/**
	 * 删除key
	 * 
	 * @param key
	 */
	public void del(String... key);

	/**
	 * 删除key
	 * 
	 * @param key
	 */
	public void del(byte... key);

	/**
	 * 
	 * 检查给定 key 是否存在。
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(String key);

	/**
	 * 将对象obj 关联到key，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 * @param value
	 */
	public void setObject(String key, Object obj);

	/**
	 * 
	 * 将对象obj 关联到key，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 * @param obj
	 * @param isExpire
	 *            是否缓存
	 */
	public void setObject(String key, Object obj, boolean isExpire);

	/**
	 * 将对象obj 关联到key，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            缓存时间，单位秒，默认10分钟(10*60)
	 */
	public void setObject(String key, Object obj, int seconds);

	/**
	 * 根据key 获取存储对象
	 * 
	 * @param key
	 * @return
	 */
	public <T> T getObject(String key);

	// public void setString(String key, String value);
	/**
	 * 将字符串值 value 关联到 key ，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 * @param value
	 * @param isExpire
	 *            是否缓存
	 */
	public void setString(String key, String value, boolean isExpire);

	/**
	 * 将字符串值 value 关联到 key ，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            缓存时间，单位秒，默认10分钟(10*60)
	 */
	public void setString(String key, String value, int seconds);

	/**
	 * 只在键 key 不存在的情况下， 将键 key 的值设置为 value 。<br/>
	 * 若键 key 已经存在， 则 SETNX 命令不做任何动作。<br/>
	 * 命令在设置成功时返回 1 ， 设置失败时返回 0 。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public long setNString(String key, String value);

	/**
	 * 将字符串值 value 关联到 key ，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 * @param value
	 * @param isExpire
	 *            是否缓存
	 */
	public void setInteger(String key, Integer value, boolean isExpire);

	/**
	 * 将字符串值 value 关联到 key ，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            缓存时间，单位秒，默认10分钟(10*60)
	 */
	public void setInteger(String key, Integer value, int seconds);

	/**
	 * 只在键 key 不存在的情况下， 将键 key 的值设置为 value 。<br/>
	 * 若键 key 已经存在， 则 SETNX 命令不做任何动作。<br/>
	 * 命令在设置成功时返回 1 ， 设置失败时返回 0 。
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public long setNInteger(String key, Integer value);

	/**
	 * 
	 * 返回与键 key 相关联的字符串值。<br/>
	 * 如果键 key 不存在， 那么返回特殊值 null , 否则， 返回键 key 的值
	 * 
	 * @param key
	 * @return
	 */
	public String getSring(String key);

	/**
	 * 
	 * 返回给定的一个或多个字符串键的值。<br/>
	 * 如果给定的字符串键里面， 有某个键不存在， 那么这个键的值将以特殊值 null 表示。
	 * 
	 * @param keys
	 * @return
	 */
	public List<String> getSring(String... keys);

	/**
	 * 为键 key 储存的数字值加上一。<br/>
	 * 如果键 key 不存在， 那么它的值会先被初始化为 0 ， 然后再执行 INCR 命令。<br/>
	 * 如果键 key 储存的值不能被解释为数字， 那么 INCR 命令将返回一个错误。
	 * 
	 * @param key
	 * @return
	 */
	public long incr(String key);

	/**
	 * 为键 key 储存的数字值加上增量 increment 。<br/>
	 * 如果键 key 不存在， 那么键 key 的值会先被初始化为 0 ， 然后再执行 INCRBY 命令。<br/>
	 * 如果键 key 储存的值不能被解释为数字， 那么 INCRBY 命令将返回一个错误。
	 * 
	 * @param key
	 * @param increment
	 * @return
	 */
	public long incr(String key, long increment);

	/**
	 * 为键 key 储存的数字值减去一。<br/>
	 * 如果键 key 不存在， 那么它的值会先被初始化为 0 ， 然后再执行 INCR 命令。<br/>
	 * 如果键 key 储存的值不能被解释为数字， 那么 INCR 命令将返回一个错误。
	 * 
	 * @param key
	 * @return
	 */
	public long decr(String key);

	/**
	 * 将键 key 储存的整数值减去减量 decrement 。<br/>
	 * 如果键 key 不存在， 那么键 key 的值会先被初始化为 0 ， 然后再执行 DECRBY 命令。<br/>
	 * 如果键 key 储存的值不能被解释为数字， 那么 DECRBY 命令将返回一个错误。
	 * 
	 * @param key
	 * @param decrement
	 * @return
	 */
	public long decr(String key, long decrement);

	// public long getStrLen(String key);

	/**
	 * 将哈希表 hash 中域 field 的值设置为 value
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public long setMapHash(String key, String field, String value);

	/**
	 * 当且仅当域 field 尚未存在于哈希表的情况下， 将它的值设置为 value。 <br />
	 * 如果给定域已经存在于哈希表当中， 那么命令将放弃执行设置操作。
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public long setNMapHash(String key, String field, String value);

	/**
	 * 同时将多个 field-value对设置到哈希表 key中。此命令会覆盖哈希表中已存在的域。
	 * 
	 * @param key
	 * @param map
	 * @return
	 */
	public String setMapHash(String key, Map<String, String> map);

	/**
	 * 返回哈希表中给定域的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String getMapHash(String key, String field);

	/**
	 * 返回哈希表 key 中，一个或多个给定域的值。 如果给定的域不存在于哈希表，那么返回一个 null 值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public List<String> getMapHash(String key, String... field);

	/**
	 * 获取哈希表中所有的field-value
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> getMapHashAll(String key);

	/**
	 * 判断哈希表中 指定的field是否存在
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public boolean hasMapHashExists(String key, String field);

	/**
	 * 删除哈希表中 key 中的一个或多个指定域，不存在的域将被忽略
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public long delMapHash(String key, String... field);

	/**
	 * Redis性能监控参数
	 * 
	 * @return
	 */
	public String MonitorInfo();

}
