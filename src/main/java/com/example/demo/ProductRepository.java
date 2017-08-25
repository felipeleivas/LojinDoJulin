package com.example.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Produto, Integer> {
	List<Produto> findByName(String name);
	Produto findByCod(Integer cod);
}
