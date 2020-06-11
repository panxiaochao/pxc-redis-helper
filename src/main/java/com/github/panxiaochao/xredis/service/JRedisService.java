package com.github.panxiaochao.xredis.service;

import java.util.List;
import java.util.Map;

import com.github.panxiaochao.xredis.utils.JRedisUtils;

import redis.clients.jedis.Jedis;

public interface JRedisService {

	/**
	 * 删除key
	 * 
	 * @param key
	 *            键值
	 */
	public void del(String... key);

	/**
	 * 删除key
	 * 
	 * @param key
	 *            键值
	 */
	public void del(byte... key);

	/**
	 * 
	 * 检查给定 key 是否存在。
	 * 
	 * @param key
	 *            键值
	 * @return 返回值
	 */
	public boolean exists(String key);

	/**
	 * 将对象obj 关联到key，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 *            键值
	 * @param obj
	 *            值
	 */
	public void setObject(String key, Object obj);

	/**
	 * 
	 * 将对象obj 关联到key，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 *            键值
	 * @param obj
	 *            对象
	 * @param isExpire
	 *            是否缓存
	 */
	public void setObject(String key, Object obj, boolean isExpire);

	/**
	 * 将对象obj 关联到key，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 *            键值
	 * @param obj
	 *            对象
	 * @param seconds
	 *            缓存时间，单位秒，默认10分钟(10*60)
	 */
	public void setObject(String key, Object obj, int seconds);

	/**
	 * 根据key 获取存储对象
	 * 
	 * @param key
	 *            键值
	 * @return 返回值
	 */
	public <T> T getObject(String key);

	// public void setString(String key, String value);
	/**
	 * 将字符串值 value 关联到 key ，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 *            键值
	 * @param value
	 *            值
	 * @param isExpire
	 *            是否缓存
	 */
	public void setString(String key, String value, boolean isExpire);

	/**
	 * 将字符串值 value 关联到 key ，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 *            键值
	 * @param value
	 *            值
	 * @param seconds
	 *            缓存时间，单位秒，默认10分钟(10*60)
	 */
	public void setString(String key, String value, int seconds);

	/**
	 * <p>
	 * 只在键 key 不存在的情况下， 将键 key 的值设置为 value
	 * </p>
	 * <p>
	 * 若键 key 已经存在， 则 SETNX 命令不做任何动作。
	 * </p>
	 * <p>
	 * 命令在设置成功时返回 1 ， 设置失败时返回 0 。
	 * </p>
	 * 
	 * @param key
	 *            键值
	 * @param value
	 *            值
	 * @return 返回值
	 */
	public long setNString(String key, String value);

	/**
	 * 将字符串值 value 关联到 key ，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 *            键值
	 * @param value
	 *            值
	 * @param isExpire
	 *            是否缓存
	 */
	public void setInteger(String key, Integer value, boolean isExpire);

	/**
	 * 将字符串值 value 关联到 key ，如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。
	 * 
	 * @param key
	 *            键值
	 * @param value
	 *            值
	 * @param seconds
	 *            缓存时间，单位秒，默认10分钟(10*60)
	 */
	public void setInteger(String key, Integer value, int seconds);

	/**
	 * <p>
	 * 只在键 key 不存在的情况下， 将键 key 的值设置为 value
	 * </p>
	 * <p>
	 * 若键 key 已经存在， 则 SETNX 命令不做任何动作
	 * </p>
	 * <p>
	 * 命令在设置成功时返回 1 ， 设置失败时返回 0
	 * </p>
	 * 
	 * @param key
	 *            键值
	 * @param value
	 *            值
	 * @return 返回值
	 */
	public long setNInteger(String key, Integer value);

	/**
	 * 
	 * <p>
	 * 返回与键 key 相关联的字符串值
	 * </p>
	 * <p>
	 * 如果键 key 不存在， 那么返回特殊值 null , 否则， 返回键 key 的值
	 * </p>
	 * 
	 * @param key
	 *            键值
	 * @return 返回值
	 */
	public String getSring(String key);

	/**
	 * 
	 * <p>
	 * 返回给定的一个或多个字符串键的值
	 * </p>
	 * <p>
	 * 如果给定的字符串键里面， 有某个键不存在， 那么这个键的值将以特殊值 null 表示
	 * </p>
	 * 
	 * @param keys
	 *            键值
	 * @return 返回值
	 */
	public List<String> getSring(String... keys);

	/**
	 * <p>
	 * 为键 key 储存的数字值加上一
	 * </p>
	 * <p>
	 * 如果键 key 不存在， 那么它的值会先被初始化为 0 ， 然后再执行 INCR 命令
	 * </p>
	 * <p>
	 * 如果键 key 储存的值不能被解释为数字， 那么 INCR 命令将返回一个错误
	 * </p>
	 * 
	 * @param key
	 *            键值
	 * @return 返回值
	 */
	public long incr(String key);

	/**
	 * <p>
	 * 为键 key 储存的数字值加上增量 increment
	 * </p>
	 * <p>
	 * 如果键 key 不存在， 那么键 key 的值会先被初始化为 0 ， 然后再执行 INCRBY 命令
	 * </p>
	 * <p>
	 * 如果键 key 储存的值不能被解释为数字， 那么 INCRBY 命令将返回一个错误
	 * </p>
	 * 
	 * @param key
	 *            键值
	 * @param increment
	 *            增量
	 * @return 返回值
	 */
	public long incr(String key, long increment);

	/**
	 * <p>
	 * 为键 key 储存的数字值减去一
	 * </p>
	 * <p>
	 * 如果键 key 不存在， 那么它的值会先被初始化为 0 ， 然后再执行 INCR 命令
	 * </p>
	 * <p>
	 * 如果键 key 储存的值不能被解释为数字， 那么 INCR 命令将返回一个错误
	 * </p>
	 * 
	 * @param key
	 *            键值
	 * @return 返回值
	 */
	public long decr(String key);

	/**
	 * <p>
	 * 将键 key 储存的整数值减去减量 decrement
	 * </p>
	 * <p>
	 * 如果键 key 不存在， 那么键 key 的值会先被初始化为 0 ， 然后再执行 DECRBY 命令
	 * </p>
	 * <p>
	 * 如果键 key 储存的值不能被解释为数字， 那么 DECRBY 命令将返回一个错误
	 * </p>
	 * 
	 * @param key
	 *            键值
	 * @param decrement
	 *            减量
	 * @return 返回值
	 */
	public long decr(String key, long decrement);

	// public long getStrLen(String key);

	/**
	 * 将哈希表 hash 中域 field 的值设置为 value
	 * 
	 * @param key
	 *            键值
	 * @param field
	 *            字段
	 * @param value
	 *            值
	 * @return 返回值
	 */
	public long setMapHash(String key, String field, String value);

	/**
	 * <p>
	 * 当且仅当域 field 尚未存在于哈希表的情况下， 将它的值设置为 value
	 * </p>
	 * <p>
	 * 如果给定域已经存在于哈希表当中， 那么命令将放弃执行设置操作
	 * </p>
	 * 
	 * @param key
	 *            键值
	 * @param field
	 *            字段
	 * @param value
	 *            值
	 * @return 返回值
	 */
	public long setNMapHash(String key, String field, String value);

	/**
	 * 同时将多个 field-value对设置到哈希表 key中。此命令会覆盖哈希表中已存在的域。
	 * 
	 * @param key
	 *            键值
	 * @param map
	 *            hashmap
	 * @return 返回值
	 */
	public String setMapHash(String key, Map<String, String> map);

	/**
	 * 返回哈希表中给定域的值
	 * 
	 * @param key
	 *            键值
	 * @param field
	 *            字段
	 * @return 返回值
	 */
	public String getMapHash(String key, String field);

	/**
	 * 返回哈希表 key 中，一个或多个给定域的值。 如果给定的域不存在于哈希表，那么返回一个 null 值
	 * 
	 * @param key
	 *            键值
	 * @param field
	 *            字段
	 * @return 返回值
	 */
	public List<String> getMapHash(String key, String... field);

	/**
	 * 获取哈希表中所有的field-value
	 * 
	 * @param key
	 *            键值
	 * @return 返回值
	 */
	public Map<String, String> getMapHashAll(String key);

	/**
	 * 判断哈希表中 指定的field是否存在
	 * 
	 * @param key
	 *            键值
	 * @param field
	 *            字段
	 * @return 返回值
	 */
	public boolean hasMapHashExists(String key, String field);

	/**
	 * 删除哈希表中 key 中的一个或多个指定域，不存在的域将被忽略
	 * 
	 * @param key
	 *            键值
	 * @param field
	 *            字段
	 * @return 返回值
	 */
	public long delMapHash(String key, String... field);

	/**
	 * <p>
	 * 将一个或多个值 value 插入到列表 key 的表头，各个 value值按从左到右的顺序依次插入到表头
	 * </p>
	 * <p>
	 * 返回值为列表长度
	 * </p>
	 * 
	 * @param key
	 *            键值
	 * @param value
	 *            值
	 * @return 返回值
	 */
	public long setLpush(String key, String... value);

	/**
	 * <p>
	 * 将一个或多个值 value 插入到列表 key 的表头，各个 value值按从右到左的顺序依次插入到表头
	 * </p>
	 * <p>
	 * 返回值为列表长度
	 * </p>
	 * 
	 * @param key
	 *            键值
	 * @param value
	 *            值
	 * @return 返回值
	 */
	public long setRpush(String key, String... value);

	/**
	 * 移除并返回列表 key 的头元素
	 * 
	 * @param key
	 *            键值
	 * @return 返回值
	 */
	public String getLpop(String key);

	/**
	 * 移除并返回列表 key 的尾元素
	 * 
	 * @param key
	 *            键值
	 * @return 返回值
	 */
	public String getRpop(String key);

	/**
	 * 返回列表 key 的长度
	 * 
	 * @param key
	 *            键值
	 * @return 返回值
	 */
	public long getLsize(String key);

	/**
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定
	 * 
	 * @param key
	 *            键值
	 * @param start
	 *            起始位置
	 * @param end
	 *            截止位置
	 * @return 返回值
	 */
	public List<String> getLrange(String key, long start, long end);

	/**
	 * Redis性能监控参数
	 * 
	 * @return 返回值
	 */
	public String MonitorInfo();

}
