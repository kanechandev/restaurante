package com.kanechan.restaurante.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kanechan.restaurante.dto.ProdutoDTO;
import com.kanechan.restaurante.services.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;

	@Operation(summary = "Lista todos os produtos com paginação")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
	})
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findAllPaged(
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
			){
		
		PageRequest pageRequest = PageRequest.of(
				page, 
				linesPerPage, 
				Direction.valueOf(direction.toUpperCase()), orderBy);
		
		Page<ProdutoDTO> produtos = produtoService.findAllPaged(pageRequest); 
		
		return ResponseEntity.ok().body(produtos);
	}
	
	@Operation(summary = "Busca um produto por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Produto econtrado"),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id){
		ProdutoDTO produtoDTO = produtoService.findById(id);
		
		return ResponseEntity.ok().body(produtoDTO);
	}
	
	@Operation(summary = "Cadastrar um produto")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
	})
	@PostMapping
	public ResponseEntity<ProdutoDTO> salvarProduto(@RequestBody @Valid ProdutoDTO produtoDTO){
		produtoDTO = produtoService.salvarProduto(produtoDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}").buildAndExpand(produtoDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(produtoDTO);
	}
	
	@Operation(summary = "Atualiza os dados do produto existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado")
	})
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO){
		produtoDTO = produtoService.atualizarProduto(id, produtoDTO);
		
		return ResponseEntity.ok().body(produtoDTO);
	}
	
	@Operation(summary = "Deleta um produto por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado")
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ProdutoDTO> deletarProduto(@PathVariable Long id){
		produtoService.deletarProduto(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
}
















