package com.gft.pricefinder.domain;

import com.gft.pricefinder.application.PriceResponse;
import com.gft.pricefinder.application.Product;
import com.gft.pricefinder.application.ProductResponse;

public class ProductMapper {

    ProductResponse mapProductToProductDto(Product first) {
        return new ProductResponse(first.getProductId(), first.getBrandId(), first.getStartDate(), first.getEndDate(),
                new PriceResponse(first.getPrice(), first.getCurrency()));
    }
}