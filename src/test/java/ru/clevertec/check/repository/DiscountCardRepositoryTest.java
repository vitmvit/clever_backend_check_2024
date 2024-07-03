package ru.clevertec.check.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.check.util.DiscountCardTestBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.clevertec.check.util.Constant.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DiscountCardRepositoryTest {

    private final DiscountCardRepository discountCardRepository = new DiscountCardRepositoryImpl();

    @Test
    public void findByNumberShouldReturnExpectedDiscountCard() {
        var expected = DiscountCardTestBuilder.builder().build().buildDiscountCard();
        var actual = discountCardRepository.findByNumber(1111, CORRECT_URL, CORRECT_USERNAME, CORRECT_PASSWORD);

        assertThat(actual)
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.id, expected.getId())
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.number, expected.getNumber())
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.amount, expected.getAmount());
    }

    @Test
    public void findByNumberShouldThrowRuntimeExceptionWhenDiscountCardNotFound() {
        var exception = assertThrows(Exception.class, () -> discountCardRepository.findByNumber(1112, CORRECT_URL, CORRECT_USERNAME, CORRECT_PASSWORD));
        assertEquals(exception.getClass(), RuntimeException.class);
    }
}
