package com.kanechan.restaurante.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.kanechan.restaurante.model.Categoria;
import com.kanechan.restaurante.model.Produto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotBlank(message = "Campo nome é obrigatório")
	private String nome;
	
	@NotNull(message = "Preço é um campo obrigatório")
	@Positive(message = "O preço deve de ser maior que zero")
	private Double preco;
	
	@NotBlank(message = "Descrição é um campo obrigatório")
	private String descricao;
	private String imgUrl;
	private LocalDateTime createdAt;
	
	@NotEmpty(message = "O produto deve de ter ao menos uma categoria relacionada")
	@Schema(description = "Lista de categorias vinculadas. Ex: [{\"id\": 1}, {\"id\": 5}]")
	private List<CategoriaDTO> categorias = new ArrayList<>();

	public ProdutoDTO() {
	}

	public ProdutoDTO(Long id, String nome, Double preco, String descricao, String imgUrl, LocalDateTime createdAt) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.descricao = descricao;
		this.imgUrl = imgUrl;
		this.createdAt = createdAt;
	}

	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.preco = produto.getPreco();
		this.descricao = produto.getDescricao();
		this.imgUrl = produto.getImgUrl();
		this.createdAt = produto.getCreatedAt();
	}
	
	public ProdutoDTO(Produto produto, Set<Categoria> categorias) {
		this(produto);
		categorias.forEach(x -> this.categorias.add(new CategoriaDTO(x)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<CategoriaDTO> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaDTO> categorias) {
		this.categorias = categorias;
	}
	
}