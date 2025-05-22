package com.kanechan.restaurante.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kanechan.restaurante.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
