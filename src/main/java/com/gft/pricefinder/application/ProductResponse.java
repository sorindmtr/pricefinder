package com.gft.pricefinder.application;

import java.time.LocalDateTime;

public record ProductResponse(Integer productId, Integer brandId, LocalDateTime startDate, LocalDateTime endDate, PriceResponse price) {
}
