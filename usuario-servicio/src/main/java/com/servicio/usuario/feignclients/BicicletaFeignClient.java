package com.servicio.usuario.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.servicio.usuario.modelos.Bicicleta;

@FeignClient(name = "bicicleta-service")
@RequestMapping("/bicicleta")
public interface BicicletaFeignClient {

	@PostMapping()
	public Bicicleta save(@RequestBody Bicicleta bicicleta);
	
	@GetMapping("/usuario/{usuarioId}")
	List<Bicicleta> getBicicletas(@PathVariable("usuarioId") int usuarioId);
	
	
}
