package com.example.demo;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StorageProductRepository extends CrudRepository<StorageProduct, Integer>{
	public StorageProduct findById(int id);
}
