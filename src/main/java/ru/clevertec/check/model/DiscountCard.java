package ru.clevertec.check.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Модель дисконтной карты.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class DiscountCard {

    private Long id;
    private Integer number;
    private Short amount;
}
