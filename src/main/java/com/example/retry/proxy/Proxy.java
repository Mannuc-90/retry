package com.example.retry.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Proxy {

	@Autowired
	private RestTemplate restTemplate;

	@Retryable(value = RuntimeException.class, maxAttempts = 4, backoff = @Backoff(multiplier = 2))
	public String retry(String pathVar) {

		System.out.println("entering retry :  path var : " + pathVar);
		if (pathVar.equals("err")) {
			throw new RuntimeException();
		}

		return restTemplate.getForObject("http://httpbin.org/delay/3", String.class);
	}
	
	@Recover
	public String recover() {
		System.out.println("Requuest failed");
		return "Recover";
	}
}
