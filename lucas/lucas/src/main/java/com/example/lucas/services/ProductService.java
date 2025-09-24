package com.example.lucas.services;

import com.example.lucas.entities.Product;
import com.example.lucas.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;
    public ProductService(ProductRepository repo) { this.repo = repo; }

    public List<Product> listAll() { return repo.findAll(); }
    public Product save(Product p) { return repo.save(p); }
    public void delete(Long id) { repo.deleteById(id); }
}
