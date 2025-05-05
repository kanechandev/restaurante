package com.kanechan.restaurante.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kanechan.restaurante.dto.CategoriaDTO;
import com.kanechan.restaurante.services.CategoriaService;


@RestController
@RequestMapping(value = "categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		List<CategoriaDTO> categorias = categoriaService.findAll(); 
		
		return ResponseEntity.ok().body(categorias);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id){
		CategoriaDTO categoriaDTO = categoriaService.findById(id);
		
		return ResponseEntity.ok().body(categoriaDTO);
	}
	
	@PostMapping
	public ResponseEntity<CategoriaDTO> salvarCategoria(@RequestBody CategoriaDTO categoriaDTO){
		categoriaDTO = categoriaService.salvarCategoria(categoriaDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}").buildAndExpand(categoriaDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(categoriaDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoriaDTO> atualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO){
		categoriaDTO = categoriaService.atualizarCategoria(id, categoriaDTO);
		
		return ResponseEntity.ok().body(categoriaDTO);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<CategoriaDTO> deletarCategoria(@PathVariable Long id){
		categoriaService.deletarCategoria(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
}
















