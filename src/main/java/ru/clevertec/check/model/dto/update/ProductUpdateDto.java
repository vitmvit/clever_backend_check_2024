package ru.clevertec.check.model.dto.update;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductUpdateDto {

    private Long id;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Boolean isWholesale;
}