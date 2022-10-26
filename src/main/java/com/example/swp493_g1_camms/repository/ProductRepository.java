package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findById(long id);
    List<Product> findByname(String name);
}
