package com.gft.pricefinder.domain;

import com.gft.pricefinder.application.ProductResponse;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProductFinderService {

    Optional<ProductResponse> getByIdDateTimeBrand(Long id, Long brandId, LocalDateTime atDateTime);
}
