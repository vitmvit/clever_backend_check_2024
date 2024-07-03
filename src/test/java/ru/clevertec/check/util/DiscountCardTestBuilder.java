package ru.clevertec.check.util;

import lombok.Builder;
import ru.clevertec.check.model.DiscountCard;

@Builder(setterPrefix = "with")
public class DiscountCardTestBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private Integer number = 1111;

    @Builder.Default
    private Short amount = 3;

    public DiscountCard buildDiscountCard() {
        return new DiscountCard(id, number, amount);
    }
}
