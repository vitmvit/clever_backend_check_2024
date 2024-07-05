package ru.clevertec.check.util;

import lombok.Builder;
import ru.clevertec.check.model.dto.DiscountCardDto;
import ru.clevertec.check.model.dto.create.DiscountCardCreateDto;
import ru.clevertec.check.model.dto.update.DiscountCardUpdateDto;
import ru.clevertec.check.model.entity.DiscountCard;

@Builder(setterPrefix = "with")
public class DiscountCardTestBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private Integer number = 7777;

    @Builder.Default
    private Short amount = 3;

    public DiscountCard buildDiscountCard() {
        return new DiscountCard(id, number, amount);
    }

    public DiscountCardDto buildDiscountCardDto() {
        var card = new DiscountCardDto();
        card.setId(id);
        card.setDiscountAmount(amount);
        card.setDiscountCard(number);
        return card;
    }

    public DiscountCardCreateDto buildDiscountCardCreateDto() {
        var card = new DiscountCardCreateDto();
        card.setDiscountAmount(amount);
        card.setDiscountCard(number);
        return card;
    }

    public DiscountCardUpdateDto buildDiscountCardUpdateDto() {
        var card = new DiscountCardUpdateDto();
        card.setId(id);
        card.setDiscountAmount(amount);
        card.setDiscountCard(number);
        return card;
    }
}
