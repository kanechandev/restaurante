package com.kanechan.restaurante.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kanechan.restaurante.dto.CategoriaDTO;
import com.kanechan.restaurante.dto.ProdutoDTO;
import com.kanechan.restaurante.model.Categoria;
import com.kanechan.restaurante.model.Produto;
import com.kanechan.restaurante.repositories.CategoriaRepository;
import com.kanechan.restaurante.repositories.ProdutoRepository;
import com.kanechan.restaurante.services.exceptions.DatabaseException;
import com.kanechan.restaurante.services.exceptions.ResourceNotFoundException;


@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);
	
	@Transactional(readOnly = true)
	public Page<ProdutoDTO> findAllPaged(PageRequest pageRequest){
		Page<Produto> produtos =  produtoRepository.findAll(pageRequest);
		
		return produtos.map(x -> new ProdutoDTO(x));
	}

	@Transactional(readOnly = true)
	public ProdutoDTO findById(Long id) {
		LOGGER.info("Entrou no método findById com o id: {}", id);
		
		Optional<Produto> obj = produtoRepository.findById(id);
		Produto produto = obj.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: "+ id));
		
		return new ProdutoDTO(produto, produto.getCategorias());
	}

	@Transactional
	public ProdutoDTO salvarProduto(ProdutoDTO produtoDTO) {
		LOGGER.info("Entrou no método salvarProduto");
		
		Produto produto = new Produto();
		copyDtoToProduto(produtoDTO, produto);
		produto= produtoRepository.save(produto);
		return new ProdutoDTO(produto);
	}

	@Transactional
	public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO) {
		LOGGER.info("Entrou no método atualizarProduto com o id: {}", id);
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: "+ id));
		
		copyDtoToProduto(produtoDTO, produto);
		
		return new ProdutoDTO(produto);
	}

	public void deletarProduto(Long id) {
		LOGGER.info("Entrou no método deletarProduto com o id: {}", id);
		
		if(!produtoRepository.existsById(id)) {
			LOGGER.warn("Produto com id {} não existe!", id);
			throw new ResourceNotFoundException("Id não encontrado: "+id);			
		}
		
		try {
			produtoRepository.deleteById(id);
			LOGGER.info("Produto com id {} deletada com sucesso!", id);
		}catch (DataIntegrityViolationException e) {
			LOGGER.error("Erro de integridade ao excluir produto com id {} : {}", id, e.getMessage());
			throw new DatabaseException("Não foi possível excluir a produto. Ela pode estar vinculada a outros registros.");
		}
	}

	private void copyDtoToProduto(ProdutoDTO produtoDTO, Produto produto) {
		produto.setDescricao(produtoDTO.getDescricao());
		produto.setImgUrl(produtoDTO.getImgUrl());
		produto.setNome(produtoDTO.getNome());
		produto.setPreco(produtoDTO.getPreco());
		
		produto.getCategorias().clear();
		
		for(CategoriaDTO categoriaDTO : produtoDTO.getCategorias()) {
			Categoria categoria = categoriaRepository.getReferenceById(categoriaDTO.getId());
			produto.getCategorias().add(categoria);
		}
	}
}




