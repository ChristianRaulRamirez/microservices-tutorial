package com.servicio.carro.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.carro.entidades.Carro;
import com.servicio.carro.repositorios.CarroRepository;

@Service
public class CarroService {

	@Autowired
	private CarroRepository carroRepository;

	public List<Carro> getAll() {
		return carroRepository.findAll();
	}

	public Carro getCarroById(int id) {
		return carroRepository.findById(id).orElse(null);
	}

	public Carro save(Carro carro) {
		Carro nuevoCarro = carroRepository.save(carro);
		return nuevoCarro;
	}
	
	public List<Carro> byUsuarioId(int usuarioId){
		return carroRepository.findByUsuarioId(usuarioId);
	}
}
