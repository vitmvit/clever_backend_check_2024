package ru.clevertec.check.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Модель товара.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Product {

    private Long id;
    private String description;
    private BigDecimal price;
    private Integer quantityInStock;
    private Boolean wholesaleProduct;
}