package com.everis.loader;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

import com.everis.model.CustomerType;
import java.util.UUID;
import reactor.core.publisher.Flux;

@Component
public class CustomerTypeLoader {
	private final ReactiveRedisConnectionFactory factory;
	private final ReactiveRedisOperations<String, CustomerType> customerTypeOps;

	public CustomerTypeLoader(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, CustomerType> customerTypeOps) {
		this.factory = factory;
		this.customerTypeOps = customerTypeOps;
	}

	@PostConstruct
	public void loadData() {
		factory
			.getReactiveConnection()
			.serverCommands()
			.flushAll()
			.thenMany(Flux
					.just("EMPRESARIAL", "PERSONAL")
					.map(name -> new CustomerType(UUID.randomUUID().toString(), name))
					.flatMap(customerType -> customerTypeOps.opsForValue().set(customerType.getId(), customerType)))
			.thenMany(customerTypeOps.keys("*")
					.flatMap(customerTypeOps.opsForValue()::get))
			.subscribe(System.out::println);
	}
}
