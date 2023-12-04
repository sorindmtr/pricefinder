package com.gft.pricefinder.domain;

import com.gft.pricefinder.application.Product;
import com.gft.pricefinder.application.ProductRepository;
import com.gft.pricefinder.application.ProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DomainProductFinderServiceTest {

    private static final LocalDateTime SOME_DATE = LocalDateTime.of(2020, 1, 1, 10, 10);

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private DomainProductFinderService productFinderService;

    @Test
    void whenFound_thenReturnMapped() {
        Product product = getProduct();
        ProductResponse productResponse = mock(ProductResponse.class);

        when(productRepository.findByProductIdAndBrandId(anyLong(), anyLong())).thenReturn(List.of(product));
        when(productMapper.mapProductToProductDto(any(Product.class))).thenReturn(productResponse);

        Optional<ProductResponse> productResponseOptional = productFinderService.getByIdDateTimeBrand(1L, 1L, SOME_DATE);

        verify(productRepository).findByProductIdAndBrandId(1L, 1L);
        verify(productMapper).mapProductToProductDto(product);
        verifyNoMoreInteractions(productRepository, productMapper);
        assertTrue(productResponseOptional.isPresent());
    }

    private static Product getProduct() {
        Product product = new Product();
        product.setStartDate(SOME_DATE.minusHours(1));
        product.setEndDate(SOME_DATE.plusHours(1));
        return product;
    }

    @Test
    void whenFoundButFilteredOut_thenReturnEmpty() {
        Product product = getProduct();
        product.setStartDate(SOME_DATE.plusHours(1));

        when(productRepository.findByProductIdAndBrandId(anyLong(), anyLong())).thenReturn(List.of(product));

        Optional<ProductResponse> productResponseOptional = productFinderService.getByIdDateTimeBrand(1L, 1L, SOME_DATE);

        verify(productRepository).findByProductIdAndBrandId(1L, 1L);
        verifyNoMoreInteractions(productRepository, productMapper);
        assertTrue(productResponseOptional.isEmpty());
    }

    @Test
    void whenNotFound_thenReturnEmpty() {
        when(productRepository.findByProductIdAndBrandId(anyLong(), anyLong())).thenReturn(Collections.emptyList());

        Optional<ProductResponse> productResponseOptional = productFinderService.getByIdDateTimeBrand(1L, 1L, SOME_DATE);

        verify(productRepository).findByProductIdAndBrandId(1L, 1L);
        verifyNoMoreInteractions(productRepository, productMapper);
        assertTrue(productResponseOptional.isEmpty());
    }

    @Test
    void whenFoundMoreWithDifferentPriorities_thenReturnHighestPriority() {
        Product product1 = getProduct();
        product1.setId(1L);
        product1.setPriority(1);
        Product product2 = getProduct();
        product2.setId(2L);
        product2.setPriority(2);
        ProductResponse productResponse = mock(ProductResponse.class);

        when(productRepository.findByProductIdAndBrandId(anyLong(), anyLong())).thenReturn(List.of(product1, product2));
        when(productMapper.mapProductToProductDto(any(Product.class))).thenReturn(productResponse);

        Optional<ProductResponse> productResponseOptional = productFinderService.getByIdDateTimeBrand(1L, 1L, SOME_DATE);
        assertTrue(productResponseOptional.isPresent());

        verify(productRepository).findByProductIdAndBrandId(1L, 1L);
        verify(productMapper).mapProductToProductDto(product2);
        verifyNoMoreInteractions(productRepository, productMapper);
    }
}