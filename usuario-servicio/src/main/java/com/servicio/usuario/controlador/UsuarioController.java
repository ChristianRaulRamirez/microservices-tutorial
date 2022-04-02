package com.servicio.usuario.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicio.usuario.modelos.Bicicleta;
import com.servicio.usuario.modelos.Carro;
import com.servicio.usuario.servicio.UsuarioService;
import com.servicio.usuario.servicio.entidades.Usuario;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios(){
		List<Usuario> usuarios = usuarioService.getAll();
		if(usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id){
		Usuario usuario = usuarioService.getUsuarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> save(@RequestBody Usuario usuario){
		Usuario usuarioNuevo = usuarioService.save(usuario);
		return ResponseEntity.ok(usuarioNuevo);
	}
	
	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackGetCarros")
	@GetMapping("/carros/{usuarioId}")
	public ResponseEntity<List<Carro>> getCarros(@PathVariable("usuarioId") int id){
		Usuario usuario = usuarioService.getUsuarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Carro> carros = usuarioService.getCarros(id);
		return ResponseEntity.ok(carros);
	}
	
	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveCarro")
	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") int usuarioId,@RequestBody Carro carro){
		if(usuarioService.getUsuarioById(usuarioId) == null) {
			return ResponseEntity.notFound().build();
		}
		Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
		return ResponseEntity.ok(nuevoCarro);
	}
	
	@CircuitBreaker(name = "bicicletasCB", fallbackMethod = "fallBackGetBicicletas")
	@GetMapping("/bicicletas/{usuarioId}")
	public ResponseEntity<List<Bicicleta>> getBicicletas(@PathVariable("usuarioId") int id){
		Usuario usuario = usuarioService.getUsuarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Bicicleta> bicicletas = usuarioService.getBicicletas(id);
		return ResponseEntity.ok(bicicletas);
	}
	
	@CircuitBreaker(name = "bicicletasCB", fallbackMethod = "fallBackSaveBicicleta")
	@PostMapping("/bicicleta/{usuarioId}")
	public ResponseEntity<Bicicleta> guardarBicicleta(@PathVariable("usuarioId") int usuarioId,@RequestBody Bicicleta bicicleta){
		if(usuarioService.getUsuarioById(usuarioId) == null) {
			return ResponseEntity.notFound().build();
		}
		Bicicleta nuevaBicicleta = usuarioService.saveBicicleta(usuarioId, bicicleta);
		return ResponseEntity.ok(nuevaBicicleta);
	}
	
	@CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetTodos")
	@GetMapping("/todos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") int usuarioId){
		Map<String, Object> resultado = usuarioService.getUsuarioAndVehiculos(usuarioId);
		return ResponseEntity.ok(resultado);
	}
	
	private ResponseEntity<List<Carro>> fallBackGetCarros(@PathVariable("usuarioId") int id,RuntimeException excepcion){
		return new ResponseEntity("El usuario : " + id + " tiene los carros en el taller",HttpStatus.OK);
	}
	
	public ResponseEntity<Carro> fallBackSaveCarro(@PathVariable("usuarioId") int usuarioId,@RequestBody Carro carro,RuntimeException excepcion){
		return new ResponseEntity("El usuario : " + usuarioId + " no tiene dinero para los carros",HttpStatus.OK);
	}
	
	private ResponseEntity<List<Bicicleta>> fallBackGetBicicletas(@PathVariable("usuarioId") int id,RuntimeException excepcion){
		return new ResponseEntity("El usuario : " + id + " tiene las motos en el taller",HttpStatus.OK);
	}
	
	private ResponseEntity<Bicicleta> fallBackSaveBicicleta(@PathVariable("usuarioId") int usuarioId,@RequestBody Bicicleta bibicleta,RuntimeException excepcion){
		return new ResponseEntity("El usuario : " + usuarioId + " no tiene dinero para las motos",HttpStatus.OK);
	}
	
	private ResponseEntity<Map<String, Object>> fallBackGetTodos(@PathVariable("usuarioId") int usuarioId,RuntimeException excepcion){
		return new ResponseEntity("El usuario : " + usuarioId + " tiene los vehiculos en el taller",HttpStatus.OK);
	}
}
