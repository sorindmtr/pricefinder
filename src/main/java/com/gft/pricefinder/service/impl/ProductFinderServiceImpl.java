package com.gft.pricefinder.service.impl;

import com.gft.pricefinder.repository.ProductRepository;
import com.gft.pricefinder.repository.dao.Product;
import com.gft.pricefinder.service.ProductFinderService;
import com.gft.pricefinder.web.dto.PriceDTO;
import com.gft.pricefinder.web.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductFinderServiceImpl implements ProductFinderService {

    private final ProductRepository productRepository;

    @Override
    public ProductDTO getByIdDateTimeBrand(Long id, LocalDateTime atDateTime, Long brandId) {
        List<Product> allByIdAndBrand = productRepository.findByProductIdAndBrandId(id, brandId);
        Product product = allByIdAndBrand.stream()
                .filter(p -> p.getStartDate().isBefore(atDateTime) && p.getEndDate().isAfter(atDateTime))
                .max(Comparator.comparing(Product::getPriority))
                .orElse(new Product());

        return mapProductToProductDto(product);
    }

    private ProductDTO mapProductToProductDto(Product first) {
        return new ProductDTO(first.getProductId(), first.getBrandId(), first.getStartDate(), first.getEndDate(),
                new PriceDTO(first.getPrice(), first.getCurrency()));
    }
}
