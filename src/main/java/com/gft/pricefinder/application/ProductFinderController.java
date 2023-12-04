package com.gft.pricefinder.application;

import com.gft.pricefinder.domain.ProductFinderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/prices")
public class ProductFinderController {

    private final ProductFinderService productFinderService;

    @GetMapping(produces = "application/json")
    public ProductResponse getByIdDateTimeBrand(@RequestParam Long productId, @RequestParam LocalDateTime dateOfPrice, @RequestParam Long brandId) {
        return productFinderService.getByIdDateTimeBrand(productId, dateOfPrice, brandId);
    }
}
