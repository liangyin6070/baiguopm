package com.baiguo.framework.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * 缓存工具类
 * @author Administrator
 *
 */
public class JedisUtils {
	private static Logger log = LoggerFactory.getLogger(JedisUtils.class);
	/*
	 * 连接池对象
	 */
	private static JedisPool pool = null;
	//Redis服务器IP
    private static String ADDR = "127.0.0.1";
    //Redis的端口号
    private static int PORT = 6379;
    //访问密码
    private static String AUTH = null;
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted。
    private static int MAX_TOTAL = 1024;
    //控制一个pool最多有多少个状态为idle的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 120000;
    
    private static int TIMEOUT = 120000;
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
	/**
	 * 初始化配置文件信息
	 */
	static {
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("redis.properties"));
			ADDR = prop.getProperty("ADDR").trim();
			PORT = Integer.parseInt(prop.getProperty("PORT").trim());
			AUTH = prop.getProperty("AUTH").trim();
			MAX_TOTAL = Integer.parseInt(prop.getProperty("MAX_TOTAL").trim());
			MAX_IDLE = Integer.parseInt(prop.getProperty("MAX_IDLE").trim());
			MAX_WAIT = Integer.parseInt(prop.getProperty("MAX_WAIT").trim());
			TIMEOUT = Integer.parseInt(prop.getProperty("TIMEOUT").trim());
			TEST_ON_BORROW = Boolean.parseBoolean(prop.getProperty("TEST_ON_BORROW").trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * title: 构建连接池
	 * date:2016-5-20
	 * author:ldw
	 */
	public static JedisPool getPool() {
		if(pool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
			config.setBlockWhenExhausted(true);
			//设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
			config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
			//是否启用pool的jmx管理功能, 默认true
			config.setJmxEnabled(true);
			//MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
			config.setJmxNamePrefix("pool");
			//是否启用后进先出, 默认true
			config.setLifo(true);
			//最大空闲连接数, 默认8个
			config.setMaxIdle(MAX_IDLE);
			//最大连接数, 默认8个
			config.setMaxTotal(MAX_TOTAL);
			//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
			config.setMaxWaitMillis(MAX_WAIT);
			//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
			config.setMinEvictableIdleTimeMillis(1800000);
			//最小空闲连接数, 默认0
			config.setMinIdle(0);
			//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
			config.setNumTestsPerEvictionRun(3);
			//对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
			config.setSoftMinEvictableIdleTimeMillis(1800000);
			//在获取连接的时候检查有效性, 默认false
			config.setTestOnBorrow(TEST_ON_BORROW);
			//在空闲时检查有效性, 默认false
			config.setTestWhileIdle(false);
			//逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
			config.setTimeBetweenEvictionRunsMillis(-1);
            if(StringUtils.isNotBlank(AUTH)) {
            	pool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
            } else {
            	pool = new JedisPool(config, ADDR, PORT, TIMEOUT);
            }
		}
		return pool;
	}
	
	 /**
     * 返还到连接池
     * 
     * @param pool 
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            pool.returnResource(redis);
        }
    }
	/**
	 * 
	 * title: 保存key-value缓存数据，在seconds秒之后就自动删除
	 * date:2016-5-20
	 * author:ldw
	 */
	public static void setValueForExpire(String key, String value, int seconds) {
		JedisPool pool = null;
        Jedis jedis = null;
        try {
        	pool = getPool();
        	jedis = pool.getResource();
        	jedis.set(key, value);
        	jedis.expire(key, seconds);
        } catch(Exception e) {
        	pool.returnBrokenResource(jedis);//释放redis对象
        	log.error("redis数据保存失败", e);
        } finally {
            returnResource(pool, jedis);//返还到连接池
        }
	}
	/**
	 * 
	 * title: 保存key-value缓存数据
	 * date:2016-5-20
	 * author:ldw
	 */
	public static void setValue(String key, String value) {
		JedisPool pool = null;
        Jedis jedis = null;
        try {
        	pool = getPool();
        	jedis = pool.getResource();
        	jedis.set(key, value);
        } catch(Exception e) {
        	pool.returnBrokenResource(jedis);//释放redis对象
        	log.error("redis数据保存失败", e);
        } finally {
            returnResource(pool, jedis);//返还到连接池
        }
	}
	
	/**
	 * 
	 * title: 获取信息
	 * date:2016-5-20
	 * author:ldw
	 */
	public static String getValue(String key) {
		String value = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);//释放redis对象
            log.error("redis查询数据失败", e);
        } finally {
            returnResource(pool, jedis);//返还到连接池
        }
        return value;
    }
	
	public static void appendValue(String key, String value) {
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.append(key, value);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);//释放redis对象
            log.error("redis查询数据失败", e);
        } finally {
            returnResource(pool, jedis);//返还到连接池
        }
	}
	/**
	 * 根据外部key添加单条key-value数据
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void setOneHashValue(String key, String field, String value) {
		JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);//释放redis对象
            log.error("redis查询数据失败", e);
        } finally {
            returnResource(pool, jedis);//返还到连接池
        }
	}
	/**
	 * 根据外部key添加多条key-value数据
	 * @param key
	 * @param map
	 */
	public static void setAllHashMapValue(String key, Map<String, String> map) {
		JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.hmset(key, map);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);//释放redis对象
            log.error("redis查询数据失败", e);
        } finally {
            returnResource(pool, jedis);//返还到连接池
        }
	}
	/**
	 * 根据外部key获取所有key-value的数据
	 * @param key
	 * @return
	 */
	public static Map<String, String> getAllHashMapValue(String key) {
		JedisPool pool = null;
        Jedis jedis = null;
        Map<String, String> cart = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            cart = jedis.hgetAll(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);//释放redis对象
            log.error("redis查询数据失败", e);
        } finally {
            returnResource(pool, jedis);//返还到连接池
        }
        return cart;
	}
	/**
	 * 根据外部key获取单条key-value的数据
	 * @param key
	 * @param field
	 * @return
	 */
	public static String getOneHashMapValue(String key, String field) {
		JedisPool pool = null;
        Jedis jedis = null;
        String cart = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            cart = jedis.hget(key, field);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);//释放redis对象
            log.error("redis查询数据失败", e);
        } finally {
            returnResource(pool, jedis);//返还到连接池
        }
        return cart;
	}
	/**
	 * 删除单条key-value值
	 * @param key
	 * @param field
	 * @return
	 */
	public static long delOneHashMapValue(String key, String field) {
		JedisPool pool = null;
        Jedis jedis = null;
        long cart = 0;
        try {
            pool = getPool();
            jedis = pool.getResource();
            cart = jedis.hdel(key, field);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);//释放redis对象
            log.error("redis查询数据失败", e);
        } finally {
            returnResource(pool, jedis);//返还到连接池
        }
        return cart;
	}
	/**
	 * 删除多条key-value值
	 * @param key
	 * @param fields
	 */
	public static void delAllHashValue(String key, String...fields) {
		JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.hdel(key, fields);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);//释放redis对象
            log.error("redis查询数据失败", e);
        } finally {
            returnResource(pool, jedis);//返还到连接池
        }
	}
}
