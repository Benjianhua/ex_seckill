package com.good.study.exseckill.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

//redis接口操作方法
@Service
public class RedisService {

	@Autowired
	private JedisPool jedisPool;
	
	
	//redis 取值
	public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey = prefix.getPrefix() + key;
			String str = jedis.get(realKey);
			T t = stringToBean(str, clazz);
			return t;
		} finally {
			returnToPool(jedis);
		}
	}
	
	//redis 塞值
	public <T> boolean set(KeyPrefix prefix, String key, T value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String str = beanToString(value);
			if (str == null || str.length() < 0) {
				return false;
			}
			String realKey = prefix.getPrefix() + key;
			int seconds = prefix.expireSeconds();
			if (seconds < 1) {
				jedis.set(realKey, str);
			} else {
				jedis.setex(realKey, seconds, str);
			}

			return true;

		} finally {
			returnToPool(jedis);
		}

	}
	
	//redis 判断key 是否存在
	public boolean exists(KeyPrefix keyPrefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey = keyPrefix.getPrefix() + key;
			return jedis.exists(realKey);
		} finally {
			returnToPool(jedis);
		}

	}
	
	//redis 删除值
	public boolean delete(KeyPrefix keyPrefix, String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String realKey = keyPrefix.getPrefix() + key;
			long ret = jedis.del(realKey);
			return ret > 0;
		} finally {
			returnToPool(jedis);
		}
	}
	
	//redis删除值
	public boolean delete(KeyPrefix prefix) {
		if (prefix == null) {
			return false;
		}

		List<String> keys = scanKeys(prefix.getPrefix());

		if (CollectionUtils.isEmpty(keys)) {
			return true;
		}

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(keys.toArray(new String[0]));
			return true;
		} finally {
			returnToPool(jedis);
		}
	}
	
	//*品配key 返回list 
	public List<String> scanKeys(String key) {
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			List<String> keys = new ArrayList<String>();
			String cursor = "0";
			ScanParams sp = new ScanParams();
			sp.match("*" + sp + "*");
			sp.count(100);
			do {
				ScanResult<String> ret = jedis.scan(cursor, sp);
				List<String> result = ret.getResult();
				if (result != null && result.size() > 0) {
					keys.addAll(result);
				}
				cursor = ret.getCursor();
			} while (!cursor.equals("0"));
			return keys;
		} finally {
			returnToPool(jedis);
		}

	}
	
	//fastjson string 转 Bean 对象
	@SuppressWarnings("unchecked")
	private static <T> T stringToBean(String str, Class<T> clazz) {
		if (str == null) {
			return null;
		}

		if (clazz == int.class || clazz == Integer.class) {
			return (T) Integer.valueOf(str);
		} else if (clazz == String.class) {
			return (T) str;
		} else if (clazz == long.class || clazz == Long.class) {
			return (T) Long.valueOf(str);
		} else {
			return JSON.toJavaObject(JSON.parseObject(str), clazz);
		}

	}
	
	//fastjson bean 转json  string 
	public static <T> String beanToString(T value) {
		if (value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if (clazz == int.class || clazz == Integer.class) {
			return "" + value;
		} else if (clazz == String.class) {
			return (String) value;
		} else if (clazz == Long.class || clazz == long.class) {
			return "" + value;
		} else {
			return JSON.toJSONString(value);
		}
	}
	
	//关闭jedis连接
	private void returnToPool(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
	
	//redis自增接口
	public <T> Long incr(KeyPrefix prefix, String key) {
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			// 生成真正的key

			String realKey = prefix.getPrefix() + key;

			return jedis.incr(realKey);

		} finally {
			returnToPool(jedis);
		}
	}
	
	//redis 自减接口
	public <T> Long decr(KeyPrefix prefix, String key) {
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			// 生成真正的key

			String realKey = prefix.getPrefix() + key;

			return jedis.decr(realKey);

		} finally {
			returnToPool(jedis);
		}
	}

}
