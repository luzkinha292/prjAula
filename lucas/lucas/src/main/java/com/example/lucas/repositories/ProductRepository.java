package com.example.lucas.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.lucas.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}