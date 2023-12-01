package com.gft.pricefinder.web.controller;

import com.gft.pricefinder.service.ProductFinderService;
import com.gft.pricefinder.web.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/prices")
public class ProductFinderController {

    private final ProductFinderService productFinderService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ProductDTO getByIdDateTimeBrand(@PathVariable Long id, @RequestParam LocalDateTime dateOfPrice, @RequestParam Long brandId) {
        return productFinderService.getByIdDateTimeBrand(id, dateOfPrice, brandId);
    }
}
