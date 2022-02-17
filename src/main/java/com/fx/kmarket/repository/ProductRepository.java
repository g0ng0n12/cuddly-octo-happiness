package com.fx.kmarket.repository;

import com.fx.kmarket.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCodeIn(List<String> codes);
}
