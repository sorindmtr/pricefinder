package com.gft.pricefinder.domain;

import com.gft.pricefinder.application.PriceResponse;
import com.gft.pricefinder.application.Product;
import com.gft.pricefinder.application.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    ProductResponse mapProductToProductDto(Product product) {
        return new ProductResponse(product.getProductId(), product.getBrandId(), product.getStartDate(), product.getEndDate(),
                new PriceResponse(product.getPrice(), product.getCurrency()));
    }
}