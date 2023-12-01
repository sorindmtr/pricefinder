package com.gft.pricefinder.service;

import com.gft.pricefinder.web.dto.ProductDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductFinderService {

    ProductDTO getByIdDateTimeBrand(Long id, LocalDateTime atDateTime, Long brandId);
}
