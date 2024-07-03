package ru.clevertec.check.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.service.impl.DiscountCardServiceImpl;
import ru.clevertec.check.util.DiscountCardTestBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.clevertec.check.util.Constant.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DiscountCardServiceTest {

    private final DiscountCardService discountCardService = new DiscountCardServiceImpl();

    @Test
    public void findByNumberShouldReturnExpectedDiscountCard() {
        var expected = DiscountCardTestBuilder.builder().build().buildDiscountCard();
        var actual = discountCardService.findByNumber(1111, CORRECT_URL, CORRECT_USERNAME, CORRECT_PASSWORD);

        assertThat(actual)
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.id, expected.getId())
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.number, expected.getNumber())
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.amount, expected.getAmount());
    }

    @Test
    public void findByNumberShouldThrowRuntimeExceptionWhenDiscountCardNotFound() {
        var exception = assertThrows(Exception.class, () -> discountCardService.findByNumber(1112, CORRECT_URL, CORRECT_USERNAME, CORRECT_PASSWORD));
        assertEquals(exception.getClass(), RuntimeException.class);
    }
}
