package ru.clevertec.check.model.dto.update;

import lombok.Getter;
import lombok.Setter;

/**
 * Модель обновления дисконтной карты.
 */
@Getter
@Setter
public class DiscountCardUpdateDto {

    private Long id;
    private Integer discountCard;
    private Short discountAmount;
}