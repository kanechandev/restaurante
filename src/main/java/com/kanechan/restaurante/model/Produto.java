package com.kanechan.restaurante.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private Double preco;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	public Produto() {
		super();
	}

	public Produto(Long id, String nome, Double preco, Categoria categoria) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoria, id, nome, preco);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(categoria, other.categoria) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome) && Objects.equals(preco, other.preco);
	}
	
}
