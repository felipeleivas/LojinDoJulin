package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesInterface extends CrudRepository<FinalSale, Long> {
	FinalSale findBySaleId(Long saleId);
}
