package com.kanechan.restaurante.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
