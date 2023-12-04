package com.gft.pricefinder.domain;

import com.gft.pricefinder.application.ProductResponse;

import java.time.LocalDateTime;

public interface ProductFinderService {

    ProductResponse getByIdDateTimeBrand(Long id, LocalDateTime atDateTime, Long brandId);
}
