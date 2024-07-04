//package ru.clevertec.check.repository;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import ru.clevertec.check.model.entity.DiscountCard;
//import ru.clevertec.check.repository.impl.DiscountCardRepositoryImpl;
//import ru.clevertec.check.util.DiscountCardTestBuilder;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class DiscountCardRepositoryTest {
//
//    private final DiscountCardRepository discountCardRepository = new DiscountCardRepositoryImpl();
//
//    @Test
//    public void findByNumberShouldReturnExpectedDiscountCard() {
//        var expected = DiscountCardTestBuilder.builder().build().buildDiscountCard();
//        var actual = discountCardRepository.findByNumber(1111);
//
//        assertThat(actual)
//                .hasFieldOrPropertyWithValue(DiscountCard.Fields.id, expected.getId())
//                .hasFieldOrPropertyWithValue(DiscountCard.Fields.number, expected.getNumber())
//                .hasFieldOrPropertyWithValue(DiscountCard.Fields.amount, expected.getAmount());
//    }
//
//    @Test
//    public void findByNumberShouldThrowRuntimeExceptionWhenDiscountCardNotFound() {
//        var exception = assertThrows(Exception.class, () -> discountCardRepository.findByNumber(1112));
//        assertEquals(exception.getClass(), RuntimeException.class);
//    }
//}
