package com.gft.pricefinder.application;

import com.gft.pricefinder.application.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductIdAndBrandId(Long id, Long brandId);
}