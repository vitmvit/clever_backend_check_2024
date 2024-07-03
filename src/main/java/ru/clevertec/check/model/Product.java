package ru.clevertec.check.model;

import lombok.*;

import java.math.BigDecimal;

/**
 * Модель товара.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;
    private String description;
    private BigDecimal price;
    private Integer quantityInStock;
    private Boolean wholesaleProduct;
}