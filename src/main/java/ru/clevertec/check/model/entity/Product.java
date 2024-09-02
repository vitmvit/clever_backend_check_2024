package ru.clevertec.check.model.entity;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

/**
 * Модель товара.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Product {

    private Long id;
    private String description;
    private BigDecimal price;
    private Integer quantityInStock;
    private Boolean wholesaleProduct;
}