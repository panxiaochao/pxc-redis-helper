package com.github.panxiaochao.xredis.utils;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JRedisUtils {
	private static final Logger log = Logger.getLogger(JRedisUtils.class);
	private static JedisPool jedisPool = null;
	// Redis服务器IP
	private static String ADDR = PropertiesUtils.getString("redis.addr");
	// Redis的端口号
	private static int PORT = PropertiesUtils.getInteger("redis.port");
	private static int MaxTotal = PropertiesUtils.getInteger("redis.maxTotal");
	private static int MaxWait = PropertiesUtils.getInteger("redis.maxWait");
	private static int MaxIdle = PropertiesUtils.getInteger("redis.maxIdle");
	// 单位是秒，默认10分钟
	public static int EXPIRE = PropertiesUtils.getInteger("redis.expire");

	static {
		initPool();
	}

	private static synchronized void initPool() {
		try {
			if (jedisPool == null) {
				// 初始化非切片池
				jedisPool = new JedisPool(getJedisPoolConfig(), ADDR, PORT, MaxWait);
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 应用个数 * maxTotal 是不能超过redis的最大连接数。
	 **/
	private static JedisPoolConfig getJedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
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
		config.setTestOnBorrow(false);
		// 在还会给pool时，是否提前进行validate操作
		config.setTestOnReturn(false);
		// 自动测试池中的空闲连接是否都是可用连接
		config.setTestWhileIdle(true);
		// 资源池中资源最小空闲时间(单位为毫秒)，达到此值后空闲资源将被移除，默认1分钟
		config.setMinEvictableIdleTimeMillis(60000);
		// 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出
		// config.setSoftMinEvictableIdleTimeMillis(500);
		// 空闲资源的检测周期(单位为毫秒)，默认30s
		config.setTimeBetweenEvictionRunsMillis(30000);
		// 做空闲资源检测时，每次的采样数，如果设置为-1，就是对所有连接做空闲监测
		config.setNumTestsPerEvictionRun(1);
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
				log.info("Jedis状态：" + jedis.isConnected());
				if (jedis.isConnected()) {
					log.info("-----	释放Redis实例	-----");
					jedis.close();
				}
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
				log.info("Jedis状态：" + jedis.isConnected());
				if (jedis.isConnected()) {
					log.info("--- 释放Redis实例 ---");
					jedis.close();
				}
			}
		}
	}
}
