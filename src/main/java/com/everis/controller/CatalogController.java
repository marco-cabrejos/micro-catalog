package com.everis.controller;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.model.CustomerType;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/catalog/customer/type")
public class CatalogController {
	private final ReactiveRedisOperations<String, CustomerType> customerTypeOps;
	CatalogController(ReactiveRedisOperations<String, CustomerType> customerTypeOps){
		this.customerTypeOps = customerTypeOps;
	}
	@GetMapping()
	public Flux<CustomerType> all() {
		return customerTypeOps.keys("*")
				.flatMap(customerTypeOps.opsForValue()::get);
	}
}
