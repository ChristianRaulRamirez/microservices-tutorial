package com.servicio.moto.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicio.moto.entidades.Bicicleta;
import com.servicio.moto.servicio.BicicletaService;

@RestController
@RequestMapping("/bicicleta")
public class BicicletaController {

	@Autowired 
	private BicicletaService bicicletaService;
	
	@GetMapping
	public ResponseEntity<List<Bicicleta>> listarBicicletas(){
		List<Bicicleta> bicicletas = bicicletaService.getAll();
		if(bicicletas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(bicicletas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Bicicleta> obtenerBicicleta(@PathVariable("id") int id){
		Bicicleta bicicleta = bicicletaService.getCarroById(id);
		if(bicicleta == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bicicleta);
	}
	
	@PostMapping
	public ResponseEntity<Bicicleta> save(@RequestBody Bicicleta carro){
		Bicicleta bicicletaNueva = bicicletaService.save(carro);
		return ResponseEntity.ok(bicicletaNueva);
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Bicicleta>> listarBicicletasPorUsuarioId(@PathVariable("usuarioId") int id){
		List<Bicicleta> bicicletas = bicicletaService.byUsuarioId(id);
		if(bicicletas.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(bicicletas);
	}
}
