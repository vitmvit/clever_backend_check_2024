package ru.clevertec.check.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {

    private Long id;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Boolean isWholesale;
}