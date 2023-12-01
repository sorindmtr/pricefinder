package com.gft.pricefinder.web.dto;

import java.time.LocalDateTime;

public record ProductDTO(Integer productId, Integer brandId, LocalDateTime startDate, LocalDateTime endDate, PriceDTO price) {
}
