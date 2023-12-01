package com.gft.pricefinder.repository;

import com.gft.pricefinder.repository.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductIdAndBrandId(Long id, Long brandId);
}