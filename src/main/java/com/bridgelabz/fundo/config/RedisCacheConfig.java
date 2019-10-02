package com.bridgelabz.fundo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import com.bridgelabz.fundo.model.UserDetailsForRegistration;



@Configuration
public class RedisCacheConfig {

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
	    return new JedisConnectionFactory();
	}

	
	@Bean
	public RedisTemplate<String, UserDetailsForRegistration> redisTemplate() {
	    RedisTemplate<String, UserDetailsForRegistration> template = new RedisTemplate<String, UserDetailsForRegistration>();
	    template.setConnectionFactory(jedisConnectionFactory());
	    template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
	    return template;
	}
}
