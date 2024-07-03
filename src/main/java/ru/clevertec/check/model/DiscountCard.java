package ru.clevertec.check.model;

import lombok.*;

/**
 * Модель дисконтной карты.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCard {

    private Long id;
    private Integer number;
    private Short amount;
}