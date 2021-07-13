package com.everis.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.everis.model.CustomerType;

@Configuration
public class CustomerTypeConfiguration {
	@Bean
	ReactiveRedisOperations<String, CustomerType> redisOperations(ReactiveRedisConnectionFactory factory) {
		Jackson2JsonRedisSerializer<CustomerType> serializer = new Jackson2JsonRedisSerializer<>(CustomerType.class);
		RedisSerializationContext.RedisSerializationContextBuilder<String, CustomerType> builder = RedisSerializationContext
				.newSerializationContext(new StringRedisSerializer());
		RedisSerializationContext<String, CustomerType> context = builder.value(serializer).build();
		return new ReactiveRedisTemplate<>(factory, context);
	}
}