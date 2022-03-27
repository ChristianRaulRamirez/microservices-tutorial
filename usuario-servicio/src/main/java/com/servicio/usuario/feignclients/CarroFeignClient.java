package com.servicio.usuario.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.servicio.usuario.modelos.Carro;

@FeignClient(name = "carro-servicio",url = "http://localhost:8002")
@RequestMapping("/carro")
public interface CarroFeignClient {

	@PostMapping()
	public Carro save(@RequestBody Carro carro);

	@GetMapping("/usuario/{usuarioId}")
	List<Carro> getCarros(@PathVariable("usuarioId") int usuarioId);
	
}
