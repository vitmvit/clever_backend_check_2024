package ru.clevertec.check.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.clevertec.check.exception.ConnectionException;
import ru.clevertec.check.model.entity.DiscountCard;
import ru.clevertec.check.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.check.util.DiscountCardTestBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DiscountCardRepositoryTest {

    private final DiscountCardRepository discountCardRepository = new DiscountCardRepositoryImpl();

    private void deleteData(Long id) {
        discountCardRepository.delete(id);
    }

    @Test
    public void findByIdShouldReturnExpectedDiscountCard() {
        var toCreate = DiscountCardTestBuilder.builder().build().buildDiscountCard();
        var created = discountCardRepository.create(toCreate);
        var actual = discountCardRepository.findById(created.getId());

        assertThat(actual.get())
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.number, toCreate.getNumber())
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.amount, toCreate.getAmount());

        deleteData(actual.get().getId());
    }

    @Test
    public void findByIdShouldThrowExceptionWhenDiscountCardNotFound() {
        var exception = assertThrows(Exception.class, () -> discountCardRepository.findById(Long.MAX_VALUE));
        assertEquals(exception.getClass(), ConnectionException.class);
    }

    @Test
    public void findByNumberShouldReturnExpectedDiscountCard() {
        var toCreate = DiscountCardTestBuilder.builder().build().buildDiscountCard();
        var created = discountCardRepository.create(toCreate);
        var actual = discountCardRepository.findByNumber(created.getNumber());

        assertThat(actual.get())
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.number, toCreate.getNumber())
                .hasFieldOrPropertyWithValue(DiscountCard.Fields.amount, toCreate.getAmount());

        deleteData(actual.get().getId());
    }

    @Test
    public void findByNumberShouldThrowExceptionWhenDiscountCardNotFound() {
        var exception = assertThrows(Exception.class, () -> discountCardRepository.findByNumber(Integer.MAX_VALUE));
        assertEquals(exception.getClass(), ConnectionException.class);
    }

    @Test
    public void createShouldReturnNewDiscountCard() {
        var toCreate = DiscountCardTestBuilder.builder().build().buildDiscountCard();
        var created = discountCardRepository.create(toCreate);

        var actual = discountCardRepository.findById(created.getId());

        assertNotNull(actual.get());
        assertEquals(toCreate.getNumber(), actual.get().getNumber());

        deleteData(created.getId());
    }

    @Test
    public void updateShouldReturnUpdatedDiscountCard() {
        var toCreate = DiscountCardTestBuilder.builder().build().buildDiscountCard();
        var created = discountCardRepository.create(toCreate);

        created.setNumber(8888);
        created.setAmount((short) 5);

        var actual = discountCardRepository.update(created);

        Assertions.assertEquals(created.getId(), actual.getId());

        deleteData(created.getId());
    }

    @Test
    public void updateShouldReturnThrowExceptionWhenDiscountCardNotFound() {
        var expected = DiscountCardTestBuilder.builder().build().buildDiscountCard();

        expected.setId(Long.MAX_VALUE);

        var exception = assertThrows(Exception.class, () -> discountCardRepository.update(expected));
        assertEquals(exception.getClass(), ConnectionException.class);
    }

    @Test
    public void delete() {
        var toCreate = DiscountCardTestBuilder.builder().build().buildDiscountCard();
        var created = discountCardRepository.create(toCreate);

        discountCardRepository.delete(created.getId());

        var exception = assertThrows(Exception.class, () -> discountCardRepository.findById(created.getId()));
        assertEquals(exception.getClass(), ConnectionException.class);
    }
}
