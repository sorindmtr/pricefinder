package com.gft.pricefinder.application;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Brand {

    @Id
    private Long id;
    private String name;
}
