package com.kanechan.restaurante.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kanechan.restaurante.dto.CategoriaDTO;
import com.kanechan.restaurante.model.Categoria;
import com.kanechan.restaurante.repositories.CategoriaRepository;
import com.kanechan.restaurante.services.exceptions.EntityNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Transactional(readOnly = true)
	public List<CategoriaDTO> findAll(){
		List<Categoria> categorias =  categoriaRepository.findAll();
		
		return categorias.stream()
				.map(CategoriaDTO::new)
				.collect(Collectors.toList());
	}

	public CategoriaDTO findById(Long id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		Categoria categoria = obj.orElseThrow(() -> new EntityNotFoundException("Recurso não encontrado."));
		
		return new CategoriaDTO(categoria);
	}
}
