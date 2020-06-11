package com.github.panxiaochao.xredis.utils;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

/**
 * pxc
 * 参数配置，参考：https://help.aliyun.com/document_detail/98726.html#section-m2c-5kr-zfb
 */
public class JRedisUtils {
	private static final Logger log = Logger.getLogger(JRedisUtils.class);
	private static JedisPool jedisPool = null;
	// Redis服务器IP
	private static String ADDR = PropertiesUtils.getString("redis.addr");
	// Redis的端口号
	private static Integer PORT = PropertiesUtils.getInteger("redis.port");
	private static Integer MaxTotal = PropertiesUtils.getInteger("redis.maxTotal");
	private static Integer MaxWait = PropertiesUtils.getInteger("redis.maxWait");
	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static Integer MaxIdle = PropertiesUtils.getInteger("redis.maxIdle");
	// 单位是秒，默认10分钟
	public static Integer EXPIRE = PropertiesUtils.getInteger("redis.expire");
	// password
	private static String PASSWORD = PropertiesUtils.getString("redis.password");
	// dbIndex
	private static Integer DBINDEX = PropertiesUtils.getInteger("redis.dbIndex");
	// TestOnBorrow
	private static boolean TestOnBorrow = PropertiesUtils.getBoolean("redis.testOnBorrow");
	// TestOnReturn
	private static boolean TestOnReturn = PropertiesUtils.getBoolean("redis.testOnReturn");

	static {
		initPool();
	}

	private static synchronized void initPool() {
		try {
			if (jedisPool == null) {
				// 初始化非切片池
				if (PASSWORD == null) {
					jedisPool = new JedisPool(getJedisPoolConfig(), ADDR, PORT, MaxWait);
				} else {
					if (DBINDEX == null) {
						jedisPool = new JedisPool(getJedisPoolConfig(), ADDR, PORT, MaxWait, PASSWORD);
					} else {
						jedisPool = new JedisPool(getJedisPoolConfig(), ADDR, PORT, MaxWait, PASSWORD, DBINDEX);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 应用个数 * maxTotal 是不能超过redis的最大连接数。 <br/>
	 * 连接池的最佳性能是maxTotal=maxIdle
	 **/
	private static JedisPoolConfig getJedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		/**
		 * 以下资源设置与使用相关参数
		 */
		// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		config.setBlockWhenExhausted(true);
		// 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
		config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
		// 是否启用pool的jmx管理功能, 默认true
		config.setJmxEnabled(true);
		// 是否启用后今后出
		config.setLifo(true);
		// 最大连接数, 默认8个，为负数的时候没有限制。
		config.setMaxTotal(MaxTotal);
		// 最大空闲连接数, 默认8个，为负数的时候没有限制。
		config.setMaxIdle(MaxIdle);
		// 最小空闲连接数, 默认0
		// config.setMinIdle(0);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWaitMillis(MaxWait);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		// 业务量很大时候建议设置为false，减少一次ping的开销。
		config.setTestOnBorrow(TestOnBorrow);
		// 在还会给pool时，是否提前进行validate操作
		// 业务量很大时候建议设置为false，减少一次ping的开销。
		config.setTestOnReturn(TestOnReturn);
		/**
		 * 以下空闲资源检测相关参数
		 */
		// 是否开启空闲资源检测
		config.setTestWhileIdle(true);
		// 资源池中资源最小空闲时间(单位为毫秒)，达到此值后空闲资源将被移除，默认1分钟
		config.setMinEvictableIdleTimeMillis(60000);
		// 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出
		// config.setSoftMinEvictableIdleTimeMillis(500);
		// 空闲资源的检测周期(单位为毫秒)，默认30s
		config.setTimeBetweenEvictionRunsMillis(30000);
		// 做空闲资源检测时，每次检测资源的个数，如果设置为-1，就是对所有连接做空闲监测
		config.setNumTestsPerEvictionRun(3);
		return config;
	}

	public synchronized static Jedis getJedis() {
		if (jedisPool == null) {
			initPool();
		}
		log.info("JedisPools线程池状态：活跃数=" + jedisPool.getNumActive() + "    空闲数=" + jedisPool.getNumIdle() + "    等待数="
				+ jedisPool.getNumWaiters());
		try {
			// if (jedisPool.isClosed() || jedisPool.getNumActive() < 0) {
			// log.info("*** 重新新建线程池jedisPool ***");
			// jedisPool = null;
			// initPool();
			// }
			if (jedisPool != null) {
				log.info("+++++	获取Redis实例	+++++");
				return jedisPool.getResource();
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("获取Redis实例失败", e);
			return null;
		}
	}

	public synchronized static void close(final Jedis jedis) {
		try {
			if (jedis != null) {
				// 高版本采用close来归还
				// log.info("Jedis状态：" + jedis.isConnected());
				// if (jedis.isConnected()) {
				log.info("-----	释放Redis实例	-----");
				jedis.close();
				// }
			}

			// 活跃数超过一定的数量时，进行关闭重新创造线程池
			// if (jedisPool.getNumActive() > MaxTotal - 100) {
			// log.info("*** jedisPool.close() ***");
			// jedisPool.close();
			// jedisPool.destroy();
			// }
		} catch (Exception e) {
			log.error("关闭Jedis失败", e);
			if (jedis != null) {
				// 高版本采用close来归还
				// log.info("Jedis状态：" + jedis.isConnected());
				// if (jedis.isConnected()) {
				log.info("--- 释放Redis实例 ---");
				jedis.close();
				// }
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Jedis jedis = JRedisUtils.getJedis();
			if (jedis != null) {
				try {
					String result = "";
					Pipeline pipeline = jedis.pipelined();
					if (true) {
						// result = jedis.setex(i + "", 100, i + "");
						result = jedis.set(i + "", i + "", "NX", "EX", 100);
					} else {
						result = jedis.set(i + "", i + "");
					}
					pipeline.sync();
					System.out.println(result);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					JRedisUtils.close(jedis);
				}
			}
		}
	}
}
