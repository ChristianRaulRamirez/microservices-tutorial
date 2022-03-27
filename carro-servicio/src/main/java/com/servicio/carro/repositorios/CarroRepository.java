package com.servicio.carro.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.carro.entidades.Carro;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Integer>{

	List<Carro> findByUsuarioId(int usuarioId);
	
}
