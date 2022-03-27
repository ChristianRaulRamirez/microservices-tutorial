package com.servicio.moto.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicio.moto.entidades.Bicicleta;
import com.servicio.moto.repositorios.BicicletaRepository;

@Service
public class BicicletaService {

	@Autowired
	private BicicletaRepository bicicletaRepository;

	public List<Bicicleta> getAll() {
		return bicicletaRepository.findAll();
	}

	public Bicicleta getCarroById(int id) {
		return bicicletaRepository.findById(id).orElse(null);
	}

	public Bicicleta save(Bicicleta bicicleta) {
		Bicicleta nuevaBicicleta = bicicletaRepository.save(bicicleta);
		return nuevaBicicleta;
	}
	
	public List<Bicicleta> byUsuarioId(int usuarioId){
		return bicicletaRepository.findByUsuarioId(usuarioId);
	}
}
