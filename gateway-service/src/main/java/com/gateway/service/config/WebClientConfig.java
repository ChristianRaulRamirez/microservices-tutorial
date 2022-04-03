package com.gateway.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

//WebClient porque estamos utilizando WebFlux y no podremos usar REST Template
@Configuration
public class WebClientConfig { 

	@Bean
	@LoadBalanced
	public WebClient.Builder builder(){
		return WebClient.builder();
	}
	
}
