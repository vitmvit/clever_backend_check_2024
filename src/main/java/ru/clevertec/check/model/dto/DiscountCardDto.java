package ru.clevertec.check.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountCardDto {

    private Long id;
    private Integer discountCard;
    private Short discountAmount;
}