package com.servicio.moto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.servicio.moto.entidades.Bicicleta;

@Repository
public interface BicicletaRepository extends JpaRepository<Bicicleta, Integer>{

	List<Bicicleta> findByUsuarioId(int usuarioId);
	
}
