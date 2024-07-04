package ru.clevertec.check.model.dto.create;

import lombok.Getter;
import lombok.Setter;

/**
 * Модель создания дисконтной карты.
 */
@Getter
@Setter
public class DiscountCardCreateDto {

    private Integer discountCard;
    private Short discountAmount;
}