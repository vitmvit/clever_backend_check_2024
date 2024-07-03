package ru.clevertec.check.model;

import lombok.*;
import lombok.experimental.FieldNameConstants;

/**
 * Модель дисконтной карты.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class DiscountCard {

    private Long id;
    private Integer number;
    private Short amount;
}