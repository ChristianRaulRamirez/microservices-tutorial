package com.servicio.usuario.servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.servicio.usuario.feignclients.BicicletaFeignClient;
import com.servicio.usuario.feignclients.CarroFeignClient;
import com.servicio.usuario.modelos.Bicicleta;
import com.servicio.usuario.modelos.Carro;
import com.servicio.usuario.repositorio.UsuarioRepository;
import com.servicio.usuario.servicio.entidades.Usuario;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CarroFeignClient carroFeignClient;
	
	@Autowired
	private BicicletaFeignClient bicicletaFeignClient;
	
	public List<Usuario> getAll(){
		return usuarioRepository.findAll();
	}
	
	public Usuario getUsuarioById(int id) {
		return usuarioRepository.findById(id).orElse(null);
	}
	
	public Usuario save(Usuario usuario) {
		Usuario nuevoUsuario = usuarioRepository.save(usuario);
		return nuevoUsuario;
	}
	
	public List<Carro> getCarros(int usuarioId){
		List<Carro> carros = restTemplate.getForObject("http://carro-service/carro/usuario/" + usuarioId, List.class);
		return carros;
	}
	
	public List<Bicicleta> getBicicletas(int usuarioId){
		List<Bicicleta> bicicletas = restTemplate.getForObject("http://bicicleta-service/bicicleta/usuario/" + usuarioId, List.class);
		return bicicletas;
	}
	
	public Carro saveCarro(int usuarioId,Carro carro) {
		carro.setUsuarioId(usuarioId);
		Carro nuevoCarro = carroFeignClient.save(carro);
		return nuevoCarro;
	}
	
	public Bicicleta saveBicicleta(int usuarioId,Bicicleta bicicleta) {
		bicicleta.setUsuarioId(usuarioId);
		Bicicleta nuevaBicicleta = bicicletaFeignClient.save(bicicleta);
		return nuevaBicicleta;
	}
	
	public Map<String,Object> getUsuarioAndVehiculos(int usuarioId){
		Map<String,Object> resultado = new HashMap<>();
		Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
	
		if(usuario == null) {
			resultado.put("Mensaje","No existe el usuario");
			return resultado;
		}
		
		resultado.put("Usuario",usuario);
		List<Carro> carros = carroFeignClient.getCarros(usuarioId);
		if(carros.isEmpty()) {
			resultado.put("Carros", "Ese usuario no tiene carros");
		}
		else {
			resultado.put("Carros",carros);
		}
		
		List<Bicicleta> bicicletas = bicicletaFeignClient.getBicicletas(usuarioId);
		if(bicicletas.isEmpty()) {
			resultado.put("Bicicletas", "Ese usuario no tiene bicicletas");
		}
		else {
			resultado.put("Bicicletas",bicicletas);
		}
		
		return resultado;
	}
}
