package com.github.panxiaochao.xredis.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.panxiaochao.xredis.service.JRedisService;
import com.github.panxiaochao.xredis.utils.JRedisUtils;
import com.github.panxiaochao.xredis.utils.SerializeUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

@Service("jRedisService")
public class JRedisServiceImpl implements JRedisService {

	private static int EXPIRE = JRedisUtils.EXPIRE;

	public void del(String... key) {
		Jedis jedis = JRedisUtils.getJedis();
		if (jedis != null) {
			try {
				jedis.del(key);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
	}

	public void del(byte... key) {
		Jedis jedis = JRedisUtils.getJedis();
		if (jedis != null) {
			try {
				jedis.del(key);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
	}

	public boolean exists(String key) {
		Jedis jedis = JRedisUtils.getJedis();
		boolean flag = false;
		if (jedis != null) {
			try {
				flag = jedis.exists(key);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return flag;
	}

	public void setObject(String key, Object value) {
		setObject(key, value, true, EXPIRE);
	}

	public void setObject(String key, Object value, boolean isExpire) {
		setObject(key, value, isExpire, EXPIRE);
	}

	public void setObject(String key, Object value, int seconds) {
		setObject(key, value, true, seconds);
	}

	public void setObject(String key, Object value, boolean isExpire, int seconds) {
		Jedis jedis = JRedisUtils.getJedis();
		if (jedis != null) {
			try {
				jedis.set(key.getBytes(), SerializeUtils.serialize(value));
				if (isExpire) {
					jedis.expire(key.getBytes(), seconds);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getObject(String key) {
		Jedis jedis = JRedisUtils.getJedis();
		byte[] byt = null;
		if (jedis != null) {
			try {
				byt = jedis.get(key.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
			if (null == byt) {
				return null;
			} else {
				return (T) SerializeUtils.unserialize(byt);
			}
		}
		return null;
	}

	public void setString(String key, String value) {
		setString(key, value, true, EXPIRE);
	}

	public void setString(String key, String value, boolean isExpire) {
		setString(key, value, isExpire, EXPIRE);
	}

	public void setString(String key, String value, int seconds) {
		setString(key, value, true, seconds);
	}

	private void setString(String key, String value, boolean isExpire, int seconds) {
		Jedis jedis = JRedisUtils.getJedis();
		if (jedis != null) {
			try {
				Pipeline pipeline = jedis.pipelined();
				if (isExpire) {
					jedis.setex(key, seconds, value);
				} else {
					jedis.set(key, value);
				}
				pipeline.sync();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
	}

	public long setNString(String key, String value) {
		Jedis jedis = JRedisUtils.getJedis();
		if (jedis != null) {
			try {
				return jedis.setnx(key, value).longValue();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return 0;
	}

	public void setInteger(String key, Integer value, boolean isExpire) {
		setString(key, String.valueOf(value), isExpire, EXPIRE);
	}

	public void setInteger(String key, Integer value, int seconds) {
		setString(key, String.valueOf(value), true, seconds);
	}

	public long setNInteger(String key, Integer value) {
		return setNString(key, String.valueOf(value));
	}

	public String getSring(String key) {
		Jedis jedis = JRedisUtils.getJedis();
		String value = "";
		if (jedis != null) {
			try {
				value = jedis.get(key);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return value;
	}

	public List<String> getSring(String... key) {
		Jedis jedis = JRedisUtils.getJedis();
		List<String> value = null;
		if (jedis != null) {
			try {
				value = jedis.mget(key);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return value;
	}

	public long incr(String key) {
		Jedis jedis = JRedisUtils.getJedis();
		long value = 0;
		if (jedis != null) {
			try {
				value = jedis.incr(key).longValue();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return value;
	}

	public long incr(String key, long increment) {
		Jedis jedis = JRedisUtils.getJedis();
		long value = 0;
		if (jedis != null) {
			try {
				value = jedis.incrBy(key, increment).longValue();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return value;
	}

	public long decr(String key) {
		Jedis jedis = JRedisUtils.getJedis();
		long value = 0;
		if (jedis != null) {
			try {
				value = jedis.decr(key).longValue();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return value;
	}

	public long decr(String key, long decrement) {
		Jedis jedis = JRedisUtils.getJedis();
		long value = 0;
		if (jedis != null) {
			try {
				value = jedis.decrBy(key, decrement).longValue();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return value;
	}

	public long getStrLen(String key) {
		Jedis jedis = JRedisUtils.getJedis();
		long value = 0;
		if (jedis != null) {
			try {
				value = jedis.strlen(key).longValue();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return value;
	}

	public long setMapHash(String key, String field, String value) {
		Jedis jedis = JRedisUtils.getJedis();
		long result = 0;
		if (jedis != null) {
			try {
				result = jedis.hset(key, field, value).longValue();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return result;
	}

	public long setNMapHash(String key, String field, String value) {
		Jedis jedis = JRedisUtils.getJedis();
		long result = 0;
		if (jedis != null) {
			try {
				result = jedis.hsetnx(key, field, value).longValue();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return result;
	}

	public String setMapHash(String key, Map<String, String> map) {
		Jedis jedis = JRedisUtils.getJedis();
		String result = "";
		if (jedis != null) {
			try {
				result = jedis.hmset(key, map);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return result;
	}

	public String getMapHash(String key, String field) {
		Jedis jedis = JRedisUtils.getJedis();
		String result = "";
		if (jedis != null) {
			try {
				result = jedis.hget(key, field);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return result;
	}

	public List<String> getMapHash(String key, String... fields) {
		Jedis jedis = JRedisUtils.getJedis();
		List<String> result = null;
		if (jedis != null) {
			try {
				result = jedis.hmget(key, fields);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return result;
	}

	public Map<String, String> getMapHashAll(String key) {
		Jedis jedis = JRedisUtils.getJedis();
		Map<String, String> result = null;
		if (jedis != null) {
			try {
				result = jedis.hgetAll(key);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return result;
	}

	public boolean hasMapHashExists(String key, String field) {
		Jedis jedis = JRedisUtils.getJedis();
		boolean result = false;
		if (jedis != null) {
			try {
				result = jedis.hexists(key, field).booleanValue();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return result;
	}

	public long delMapHash(String key, String... fields) {
		Jedis jedis = JRedisUtils.getJedis();
		long result = 0;
		if (jedis != null) {
			try {
				result = jedis.hdel(key, fields).longValue();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		return result;
	}

	public String MonitorInfo() {
		Jedis jedis = JRedisUtils.getJedis();
		String result = "";
		if (jedis != null) {
			try {
				result = jedis.info();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JRedisUtils.close(jedis);
			}
		}
		if (StringUtils.isNotBlank(result)) {
			Properties proper = new Properties();
			try {
				proper.load(new StringReader(result));
				// 去除路径key
				proper.remove("config_file");
				proper.remove("executable");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return proper.toString();
		}
		return result;
	}
}
