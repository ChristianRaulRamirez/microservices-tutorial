package com.gateway.service.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.server.ServerWebExchange;

import com.gateway.service.dto.TokenDTO;

import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config>{
	
	private WebClient.Builder webClient;
	
	public AuthFilter(Builder webClient) {
		super(Config.class);
		this.webClient = webClient;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange,chain) -> {
			if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) { //comprobamos que el token tenga Authorization
				return onError(exchange, HttpStatus.BAD_REQUEST);
			}
			String tokenHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String[] chunks = tokenHeader.split(" "); //separamos con espacios
			
			if(chunks.length != 2 || !chunks[0].equals("Bearer")) { //Bearer es la posicion 0 y token el la posicion 1
				return onError(exchange, HttpStatus.BAD_REQUEST);
			}
			
			return webClient.build()
					.post()
					.uri("http://auth-service/auth/validate?token=" + chunks[1])
					.retrieve().bodyToMono(TokenDTO.class)
					.map(t -> {
						t.getToken();
						return exchange;
					}).flatMap(chain::filter);
		});
	}
	
	public Mono<Void> onError(ServerWebExchange exchange,HttpStatus status){ //este status va a venir del Auth service cuando ocurra un fallo
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		return response.setComplete();
	}
	
	public static class Config{
		
	}
	
}
