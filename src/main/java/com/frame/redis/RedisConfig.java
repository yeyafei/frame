package com.frame.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

/**
 * RedisConfig
 * 
 * @author yyf
 * @date 2018年11月6日
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

	@Value("${redis.sentinel.ips}")
	private String redisHosts;
	@Value("${redis.master.name}")
	private String master;
	@Value("${redis.pool.maxIdle:300}")
	private int maxIdle;
	@Value("${redis.pool.maxActive:600}")
	private int maxTotal;
	@Value("${redis.pool.maxWait:3000}")
	private int maxWaitMillis;
	@Value("${redis.pool.testOnBorrow:true}")
	private boolean testOnBorrow;
	@Value("${redis.password}")
	private String password;

	@Autowired
	private JedisPoolConfig jedisPoolConfig;

	@Autowired
	private RedisSentinelConfiguration redisSentinelConfiguration;

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMinIdle(10);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		return jedisPoolConfig;
	}

	@Bean
	public RedisSentinelConfiguration redisSentinelConfiguration() {
		RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
		String[] host = redisHosts.split(",");
		for (String redisHost : host) {
			String[] item = redisHost.split(":");
			String ip = item[0];
			String port = item[1];
			redisSentinelConfiguration.addSentinel(new RedisNode(ip, Integer.parseInt(port)));
		}
		if (StringUtils.isNotBlank(password)) {
			redisSentinelConfiguration.setPassword(RedisPassword.of(password));
		}
		redisSentinelConfiguration.setMaster(master);
		return redisSentinelConfiguration;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisSentinelConfiguration,
				jedisPoolConfig);
		return jedisConnectionFactory;
	}

}
