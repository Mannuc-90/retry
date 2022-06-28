package com.example.retry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.retry.proxy.Proxy;


@RestController
public class Controller {

	@Autowired
	private Proxy proxy;
	
	@Autowired
	private CircuitBreakerFactory cbf;
	
	@GetMapping("/retry/{pathVar}")
	public String retryEvent(@PathVariable String pathVar) {
		CircuitBreaker cb = cbf.create("c1");
		return cb.run(()-> proxy.retry(pathVar));
		//return proxy.retry(pathVar);
	}
}
