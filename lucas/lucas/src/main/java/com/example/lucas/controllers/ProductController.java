package com.example.lucas.controllers;

import com.example.lucas.entities.*;
import com.example.lucas.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService ps) { this.productService = ps; }

    @GetMapping
    public ResponseEntity<?> list() {
        List<Product> p = productService.listAll();
        return ResponseEntity.ok(p);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Product prod) {
        return ResponseEntity.ok(productService.save(prod));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
