package com.gft.pricefinder.domain;

import com.gft.pricefinder.application.Product;
import com.gft.pricefinder.application.ProductResponse;
import com.gft.pricefinder.application.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DomainProductFinderService implements ProductFinderService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Optional<ProductResponse> getByIdDateTimeBrand(Long id, Long brandId, LocalDateTime atDateTime) {
        List<Product> allByIdAndBrand = productRepository.findByProductIdAndBrandId(id, brandId);

        return allByIdAndBrand.stream()
                .filter(p -> p.getStartDate().isBefore(atDateTime) && p.getEndDate().isAfter(atDateTime))
                .max(Comparator.comparing(Product::getPriority))
                .map(productMapper::mapProductToProductDto);
    }
}
