package ru.clevertec.check.model.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
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
@NoArgsConstructor
@FieldNameConstants
@JsonPropertyOrder({"name", "age"})
public class Check {

    private LocalDateTime date;
    private List<ProductData> productDataList;
    private DiscountCard discountCard;
    private BigDecimal totalSum;
    private BigDecimal totalDiscount;
    private BigDecimal totalSumWithDiscount;
}