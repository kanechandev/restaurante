package com.kanechan.restaurante.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kanechan.restaurante.model.Categoria;
import com.kanechan.restaurante.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Transactional(readOnly = true)
	public List<Categoria> findAll(){
		return categoriaRepository.findAll();
	}
}
