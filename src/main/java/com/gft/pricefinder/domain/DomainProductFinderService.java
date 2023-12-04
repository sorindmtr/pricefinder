package com.gft.pricefinder.domain;

import com.gft.pricefinder.application.Product;
import com.gft.pricefinder.application.PriceResponse;
import com.gft.pricefinder.application.ProductResponse;
import com.gft.pricefinder.application.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class DomainProductFinderService implements ProductFinderService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse getByIdDateTimeBrand(Long id, LocalDateTime atDateTime, Long brandId) {
        List<Product> allByIdAndBrand = productRepository.findByProductIdAndBrandId(id, brandId);
        Product product = allByIdAndBrand.stream()
                .filter(p -> p.getStartDate().isBefore(atDateTime) && p.getEndDate().isAfter(atDateTime))
                .max(Comparator.comparing(Product::getPriority))
                .orElse(new Product());

        return mapProductToProductDto(product);
    }

    private ProductResponse mapProductToProductDto(Product first) {
        return new ProductResponse(first.getProductId(), first.getBrandId(), first.getStartDate(), first.getEndDate(),
                new PriceResponse(first.getPrice(), first.getCurrency()));
    }
}
