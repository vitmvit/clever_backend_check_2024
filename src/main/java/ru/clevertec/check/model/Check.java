package ru.clevertec.check.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Модель чека.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@FieldNameConstants
public class Check {

    private LocalDateTime date;
    private List<ProductData> productDataList;
    private DiscountCard discountCard;
    private BigDecimal totalSum;
    private BigDecimal totalDiscount;
    private BigDecimal totalSumWithDiscount;
}