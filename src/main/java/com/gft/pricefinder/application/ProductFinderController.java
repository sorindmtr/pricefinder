package com.gft.pricefinder.application;

import com.gft.pricefinder.domain.ProductFinderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/prices")
public class ProductFinderController {

    private static final String NUMBER_ERROR_MESSAGE = "Please provide a positive number for the ";

    private final ProductFinderService productFinderService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getByIdDateTimeBrand(@RequestParam Long productId, @RequestParam Long brandId, @RequestParam LocalDateTime dateOfPrice) {
        if (productId <= 0) {
            return ResponseEntity.badRequest().body(NUMBER_ERROR_MESSAGE + "productId");
        } else if (brandId <= 0) {
            return ResponseEntity.badRequest().body(NUMBER_ERROR_MESSAGE + "brandId");
        }

        Optional<ProductResponse> productResponse = productFinderService.getByIdDateTimeBrand(productId, brandId, dateOfPrice);
        return productResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleParametersMismatchException(MethodArgumentTypeMismatchException ex) {
        String parameter = ex.getName();

        return switch (parameter) {
            case "productId", "brandId" -> ResponseEntity.badRequest().body(NUMBER_ERROR_MESSAGE + parameter);
            case "dateOfPrice" ->
                    ResponseEntity.badRequest().body("Please provide a date format like \"YYYY-MM-DDTHH:mm:ss\" for the " + parameter);
            default -> ResponseEntity.badRequest().body("Unknown argument!");
        };
    }
}
