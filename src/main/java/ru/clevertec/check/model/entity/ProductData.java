package ru.clevertec.check.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Модель данных о товаре в чеке.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductData {

    private int count;
    private Product product;
    private BigDecimal discount;
    private BigDecimal totalSum;
}