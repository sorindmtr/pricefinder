package com.gft.pricefinder;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductFinderIntegrationTest {

    private static final String url = "/prices";
    private static final String productId = "35455";
    private static final String brandId = "1";
    private static final LocalDateTime validDateTime = LocalDateTime.of(2020, 6, 14, 10, 0);

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @ParameterizedTest
    @MethodSource("pricesByDate")
    void getDifferentPricesByProductAndTime(LocalDateTime dateTime, Double price) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url).param("productId", productId)
                        .param("dateOfPrice", dateTime.toString()).param("brandId", brandId))
                .andExpect(status().isOk()).andExpect(jsonPath("$.price.price").value(price));
    }

    private static Stream<Arguments> pricesByDate() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2020, 6, 14, 10, 0, 0), 35.5),
                Arguments.of(LocalDateTime.of(2020, 6, 14, 16, 0, 0), 25.45),
                Arguments.of(LocalDateTime.of(2020, 6, 14, 21, 0, 0), 35.5),
                Arguments.of(LocalDateTime.of(2020, 6, 15, 10, 0, 0), 30.5),
                Arguments.of(LocalDateTime.of(2020, 6, 16, 21, 0, 0), 38.95),
                Arguments.of(LocalDateTime.of(2023, 6, 16, 21, 0, 0), null)
        );
    }

    @Test
    void whenBadId_shouldGetBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url).param("productId", "-1")
                        .param("dateOfPrice", validDateTime.toString()).param("brandId", brandId))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$").value("Please provide a positive number for the productId"));
    }

    @Test
    void whenBadIdType_shouldGetBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url).param("productId", "AB")
                        .param("dateOfPrice", validDateTime.toString()).param("brandId", brandId))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$").value("Please provide a positive number for the productId"));
    }

    @Test
    void whenBadDate_shouldGetBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url).param("productId", productId)
                        .param("dateOfPrice", "1919-31-13T92:19:1").param("brandId", brandId))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$").value("Please provide a date format like \"YYYY-MM-DDTHH:mm:ss\" for the dateOfPrice"));
    }

    @Test
    void whenNotFound_shouldGetNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(url).param("productId", "1")
                        .param("dateOfPrice", validDateTime.toString()).param("brandId", brandId))
                .andExpect(status().isNotFound());
    }
}
