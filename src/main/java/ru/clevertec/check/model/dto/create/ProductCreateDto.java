package ru.clevertec.check.model.dto.create;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductCreateDto {

    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Boolean isWholesale;
}