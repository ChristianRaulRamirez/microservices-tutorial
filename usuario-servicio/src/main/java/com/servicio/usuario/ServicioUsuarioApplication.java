package com.servicio.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServicioUsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioUsuarioApplication.class, args);
	}

}
