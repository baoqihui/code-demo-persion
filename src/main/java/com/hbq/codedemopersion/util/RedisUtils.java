package com.hbq.codedemopersion.util;

import cn.hutool.core.convert.Convert;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 *
 * @author hbq
 */
@Component
@Data
@Slf4j
@NoArgsConstructor
public class RedisUtils {
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.database}")
	private int database;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	public RedisUtils(String host, String password, int port, String database) {
		this.host = host;
		this.password = password;
		this.port = port;
		this.database = Convert.toInt(database);
		this.stringRedisTemplate = getTemTemplate();
	}
	/**
	 * 实现命令：TTL key，以秒为单位，返回给定 key的剩余生存时间(TTL, time to live)。
	 *
	 * @param key
	 * @return
	 */
	public long ttl(String key) {
		return stringRedisTemplate.getExpire(key);
	}

	/**
	 * 实现命令：expire 设置过期时间，单位秒
	 *
	 * @param key
	 * @return
	 */
	public void expire(String key, long timeout) {
		stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 是否存在key
	 *
	 * @param key
	 */
	public Boolean hasKey(String key) {
		return stringRedisTemplate.hasKey(key);
	}

	/**
	 * 实现命令：INCR key，增加key一次
	 *
	 * @param key
	 * @return
	 */
	public long incr(String key, long delta) {
		return stringRedisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 实现命令：KEYS pattern，查找所有符合给定模式 pattern的 key
	 */
	public Set<String> keys(String pattern) {
		return stringRedisTemplate.keys(pattern);
	}

	/**
	 * 实现命令：DEL key，删除一个key
	 *
	 * @param key
	 */
	public void del(String key) {
		stringRedisTemplate.delete(key);
	}

	// String（字符串）

	/**
	 * 实现命令：SET key value，设置一个key-value（将字符串值 value关联到 key）
	 *
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 实现命令：SET key value EX seconds，设置key-value和超时时间（秒）
	 *
	 * @param key
	 * @param value
	 * @param timeout （以秒为单位）
	 */
	public void set(String key, String value, long timeout) {
		stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 实现命令：GET key，返回 key所关联的字符串值。
	 *
	 * @param key
	 * @return value
	 */
	public String get(String key) {
		return (String) stringRedisTemplate.opsForValue().get(key);
	}

	/**
	 * 批量查询，对应mget
	 *
	 * @param keys
	 * @return
	 */
	public List<String> mget(List<String> keys) {
		return stringRedisTemplate.opsForValue().multiGet(keys);
	}

	/**
	 * 批量查询，管道pipeline
	 *
	 * @param keys
	 * @return
	 */
	public List<Object> batchGet(List<String> keys) {

		List<Object> result = stringRedisTemplate.executePipelined(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection src = (StringRedisConnection) connection;

				for (String k : keys) {
					src.get(k);
				}
				return null;
			}
		});

		return result;
	}


	// Hash（哈希表）

	/**
	 * 实现命令：HSET key field value，将哈希表 key中的域 field的值设为 value
	 *
	 * @param key
	 * @param field
	 * @param value
	 */
	public void hset(String key, String field, Object value) {
		stringRedisTemplate.opsForHash().put(key, field, value);
	}

	/**
	 * 实现命令：HGET key field，返回哈希表 key中给定域 field的值
	 *
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(String key, String field) {
		return (String) stringRedisTemplate.opsForHash().get(key, field);
	}

	/**
	 * 实现命令：HDEL key field [field ...]，删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 *
	 * @param key
	 * @param fields
	 */
	public void hdel(String key, Object... fields) {
		stringRedisTemplate.opsForHash().delete(key, fields);
	}

	/**
	 * 实现命令：HGETALL key，返回哈希表 key中，所有的域和值。
	 *
	 * @param key
	 * @return
	 */
	public Map<Object, Object> hgetall(String key) {
		return stringRedisTemplate.opsForHash().entries(key);
	}

	// List（列表）

	/**
	 * 实现命令：LPUSH key value，将一个值 value插入到列表 key的表头
	 *
	 * @param key
	 * @param value
	 * @return 执行 LPUSH命令后，列表的长度。
	 */
	public long lpush(String key, String value) {
		return stringRedisTemplate.opsForList().leftPush(key, value);
	}

	/**
	 * 实现命令：LPOP key，移除并返回列表 key的头元素。
	 *
	 * @param key
	 * @return 列表key的头元素。
	 */
	public String lpop(String key) {
		return (String) stringRedisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 实现命令：RPUSH key value，将一个值 value插入到列表 key的表尾(最右边)。
	 *
	 * @param key
	 * @param value
	 * @return 执行 LPUSH命令后，列表的长度。
	 */
	public long rpush(String key, String value) {
		return stringRedisTemplate.opsForList().rightPush(key, value);
	}

	/**
	 * 实现命令：RPOP key，移除并返回列表最右边的元素。
	 *
	 * @param key
	 * @return 列表key的最右边元素。
	 */
	public String rpop(String key) {
		return (String) stringRedisTemplate.opsForList().rightPop(key);
	}
	/**
	 * 实现命令：BRPOP key，移除并返回列表最右边的元素,阻塞时间。
	 *
	 * @param key
	 * @return 列表key的最右边元素。
	 */
	public String brpop(String key, Long timeout, TimeUnit unit) {
		return stringRedisTemplate.opsForList().rightPop(key, timeout, unit);
	}
	/**
	 * 实现命令：LINDEX key index
	 *
	 * @return index对应的值
	 */
	public String lindex(String key, Integer index) {
		return stringRedisTemplate.opsForList().index(key, index);
	}

	/**
	 * 实现命令：LTRIM key start end 修剪list
	 * 清空list：ltrim key 1 0（ltrim key start end 中的start要比end大即可，数值且都为正数。）
	 */
	public void ltrim(String key, Long start, Long end) {
		stringRedisTemplate.opsForList().trim(key, start, end);
	}


	/**
	 * 实现命令：LLEN key 获取list长度
	 */
	public Long llen(String key) {
		return stringRedisTemplate.opsForList().size(key);
	}

	/**
	 * 如果key不存在那么放入value
	 *
	 * @param key
	 * @param value
	 * @param timeout
	 * @return boolean
	 * @return void
	 * @author yabin.zhang
	 * @date 2021/11/30 14:04
	 */
	public Boolean setnx(String key, String value, long timeout) {
		return stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 向set中添加元素
	 *
	 * @param key   set的key
	 * @param value set的value
	 */
	public void sAdd(String key, String value) {
		stringRedisTemplate.opsForSet().add(key, value);
	}

	public void zsAdd(String key, String value,double score) {
		stringRedisTemplate.opsForZSet().add(key,value,score);
	}

	public Map<String,Object> zsMembers(String key, long score0,long score1) {
		Set<ZSetOperations.TypedTuple<String>> tuples  = stringRedisTemplate.opsForZSet().rangeWithScores(key, score0, score1);
		ZSetOperations.TypedTuple<String> o = (ZSetOperations.TypedTuple<String>)tuples.toArray()[0];
		return Map.of("source",o.getScore(),"value",o.getValue());
	}
	public void zrem(String key,String value) {
		stringRedisTemplate.opsForZSet().remove(key,value);
	}
	/**
	 * set中所有数据
	 *
	 * @param key set的key
	 * @return set中所有数据
	 */
	public Set<String> sMembers(String key) {
		return stringRedisTemplate.opsForSet().members(key);
	}

	/**
	 * 删除并返回set中的一个元素
	 *
	 * @param key set的key
	 * @return set中的一个元素
	 */
	public String sPop(String key) {
		return stringRedisTemplate.opsForSet().pop(key);
	}

	/**
	 * 获取key的大小
	 *
	 * @param key set的key
	 * @return key的大小
	 */
	public Long sCard(String key) {
		return stringRedisTemplate.opsForSet().size(key);
	}

	public StringRedisTemplate getTemTemplate(){
		// 构建工厂对象
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
		config.setDatabase(this.database);
		config.setHostName(host);
		config.setPort(port);
		config.setPassword(RedisPassword.of(password));
		LettuceConnectionFactory factory = new LettuceConnectionFactory(config,
				LettucePoolingClientConfiguration.builder().build());
		// 重新初始化工厂
		factory.afterPropertiesSet();
		return new StringRedisTemplate(factory);
	}
}
