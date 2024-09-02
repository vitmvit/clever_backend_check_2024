package ru.clevertec.check.model.dto.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountCardUpdateDto {

    private Long id;
    private Integer discountCard;
    private Short discountAmount;
}