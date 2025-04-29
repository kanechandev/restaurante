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

	@Transactional(readOnly = true)
	public CategoriaDTO findById(Long id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		Categoria categoria = obj.orElseThrow(() -> new EntityNotFoundException("Recurso não encontrado."));
		
		return new CategoriaDTO(categoria);
	}

	@Transactional
	public CategoriaDTO salvarCategoria(CategoriaDTO categoriaDTO) {
		Categoria categoria = new Categoria();
		categoria.setNome(categoriaDTO.getNome());
		categoria= categoriaRepository.save(categoria);
		return new CategoriaDTO(categoria);
	}

	@Transactional
	public CategoriaDTO atualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Id não encontrado: "+ id));
		
		categoria.setNome(categoriaDTO.getNome());
		
		return new CategoriaDTO(categoria);
	}
}
