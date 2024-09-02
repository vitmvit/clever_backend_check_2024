package ru.clevertec.check.converter;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.model.entity.DiscountCard;
import ru.clevertec.check.util.DiscountCardTestBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountCardConverterTest {

    private final DiscountCardConverter discountCardConverter = new DiscountCardConverterImpl();

    @Test
    public void discountCardToDiscountCardDtoTest() {
        var expected = DiscountCardTestBuilder.builder().build().buildDiscountCard();

        var actual = discountCardConverter.convert(expected);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getNumber(), actual.getDiscountCard());
        assertEquals(expected.getAmount(), actual.getDiscountAmount());
    }

    @Test
    public void discountCardCreateDtoToDiscountCardTest() {
        var expected = DiscountCardTestBuilder.builder().build().buildDiscountCard();
        var toCreate = DiscountCardTestBuilder.builder().build().buildDiscountCardCreateDto();

        var actual = discountCardConverter.convert(toCreate);

        assertThat(actual)
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.number, expected.getNumber())
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.amount, expected.getAmount());
    }

    @Test
    public void mergeTest() {
        var expected = DiscountCardTestBuilder.builder().build().buildDiscountCard();
        var toUpdate = DiscountCardTestBuilder.builder().build().buildDiscountCardUpdateDto();

        var actual = discountCardConverter.merge(expected, toUpdate);

        assertThat(actual)
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.amount, expected.getAmount())
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.number, expected.getNumber());
    }
}
