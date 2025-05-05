package com.kanechan.restaurante.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kanechan.restaurante.dto.CategoriaDTO;
import com.kanechan.restaurante.model.Categoria;
import com.kanechan.restaurante.repositories.CategoriaRepository;
import com.kanechan.restaurante.services.exceptions.DatabaseException;
import com.kanechan.restaurante.services.exceptions.ResourceNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
	
	@Transactional(readOnly = true)
	public List<CategoriaDTO> findAll(){
		List<Categoria> categorias =  categoriaRepository.findAll();
		
		return categorias.stream()
				.map(CategoriaDTO::new)
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoriaDTO findById(Long id) {
		LOGGER.info("Entrou no método findById com o id: {}", id);
		
		Optional<Categoria> obj = categoriaRepository.findById(id);
		Categoria categoria = obj.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado."));
		
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
				.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: "+ id));
		
		categoria.setNome(categoriaDTO.getNome());
		
		return new CategoriaDTO(categoria);
	}

	public void deletarCategoria(Long id) {
		LOGGER.info("Entrou no método deletarCategoria com o id: {}", id);
		
		if(!categoriaRepository.existsById(id)) {
			LOGGER.warn("Categoria com id {} não existe!", id);
			throw new ResourceNotFoundException("Id não encontrado: "+id);			
		}
		
		try {
			categoriaRepository.deleteById(id);
			LOGGER.info("Categoria com id {} deletada com sucesso!", id);
		}catch (DataIntegrityViolationException e) {
			LOGGER.error("Erro de integridade ao excluir categoria com id {} : {}", id, e.getMessage());
			throw new DatabaseException("Não foi possível excluir a categoria. Ela pode estar vinculada a outros registros.");
		}
	}
}