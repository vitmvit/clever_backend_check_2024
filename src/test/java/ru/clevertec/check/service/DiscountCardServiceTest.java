package ru.clevertec.check.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.exception.ConnectionException;
import ru.clevertec.check.service.impl.DiscountCardServiceImpl;
import ru.clevertec.check.util.DiscountCardTestBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiscountCardServiceTest {

    @Mock
    private DiscountCardServiceImpl discountCardService;

    @Test
    void findByIdShouldReturnExpectedDiscountCardWhenFound() {
        var expected = DiscountCardTestBuilder.builder().build().buildDiscountCardDto();
        var id = expected.getId();

        when(discountCardService.findById(id)).thenReturn(expected);
        var actual = discountCardService.findById(id);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDiscountCard(), actual.getDiscountCard());
        assertEquals(expected.getDiscountAmount(), actual.getDiscountAmount());
    }

    @Test
    void findByIdShouldThrowConnectionExceptionWhenNotFound() {
        doThrow(ConnectionException.class).when(discountCardService).findById(Long.MAX_VALUE);

        var exception = assertThrows(ConnectionException.class, () -> discountCardService.findById(Long.MAX_VALUE));
        assertEquals(exception.getClass(), ConnectionException.class);
    }

    @Test
    void createShouldReturnNewDiscountCard() {
        var expected = DiscountCardTestBuilder.builder().build().buildDiscountCardDto();
        var cardToCreate = DiscountCardTestBuilder.builder().withId(null).build().buildDiscountCardCreateDto();

        when(discountCardService.create(cardToCreate)).thenReturn(expected);
        var actual = discountCardService.create(cardToCreate);

        assertNotNull(actual.getId());
        assertEquals(expected.getDiscountCard(), actual.getDiscountCard());
        assertEquals(expected.getDiscountAmount(), actual.getDiscountAmount());
    }

    @Test
    public void updateShouldUpdatedDiscountCard() {
        var id = DiscountCardTestBuilder.builder().build().buildDiscountCard().getId();
        var cardToUpdate = DiscountCardTestBuilder.builder().build().buildDiscountCardUpdateDto();
        var expected = DiscountCardTestBuilder.builder().build().buildDiscountCardDto();

        when(discountCardService.update(id, cardToUpdate)).thenReturn(expected);
        var actual = discountCardService.update(id, cardToUpdate);

        assertEquals(expected.getDiscountCard(), actual.getDiscountCard());
        assertEquals(expected.getDiscountAmount(), actual.getDiscountAmount());
    }

    @Test
    void delete() {
        Long id = DiscountCardTestBuilder.builder().build().buildDiscountCard().getId();
        doNothing().when(discountCardService).delete(id);

        assertDoesNotThrow(() -> discountCardService.delete(id));
    }
}
