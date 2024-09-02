package ru.clevertec.check.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.converter.DiscountCardConverterImpl;
import ru.clevertec.check.exception.ConnectionException;
import ru.clevertec.check.exception.NotFoundException;
import ru.clevertec.check.model.entity.DiscountCard;
import ru.clevertec.check.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.check.service.impl.DiscountCardServiceImpl;
import ru.clevertec.check.util.DiscountCardTestBuilder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountCardServiceTest {

    @Mock
    private DiscountCardRepositoryImpl discountCardRepository;

    @Mock
    private DiscountCardConverterImpl discountCardConverter;

    @InjectMocks
    private DiscountCardServiceImpl discountCardService;

    @Captor
    private ArgumentCaptor<DiscountCard> discountCardArgumentCaptor;

    @Test
    void findByIdShouldReturnExpectedDiscountCardWhenFound() {
        var expectedDiscountCard = DiscountCardTestBuilder.builder().build().buildDiscountCard();
        var expectedDiscountCardDto = DiscountCardTestBuilder.builder().build().buildDiscountCardDto();
        var discountCardId = expectedDiscountCard.getId();

        when(discountCardRepository.findById(discountCardId)).thenReturn(Optional.of(expectedDiscountCard));
        when(discountCardConverter.convert(expectedDiscountCard)).thenReturn(expectedDiscountCardDto);

        var actual = discountCardService.findById(discountCardId);

        assertEquals(expectedDiscountCardDto.getId(), actual.getId());
        assertEquals(expectedDiscountCardDto.getDiscountCard(), actual.getDiscountCard());
        assertEquals(expectedDiscountCardDto.getDiscountAmount(), actual.getDiscountAmount());
    }

    @Test
    void findByIdShouldThrowConnectionExceptionWhenNotFound() {
        doThrow(ConnectionException.class).when(discountCardRepository).findById(Long.MAX_VALUE);

        var exception = assertThrows(ConnectionException.class, () -> discountCardService.findById(Long.MAX_VALUE));
        assertEquals(exception.getClass(), ConnectionException.class);
    }

    @Test
    void createShouldReturnNewDiscountCard() {
        var discountCardToSave = DiscountCardTestBuilder.builder().withId(null).build().buildDiscountCard();
        var expectedDiscountCard = DiscountCardTestBuilder.builder().build().buildDiscountCard();
        var discountCardCreateDto = DiscountCardTestBuilder.builder().withId(null).build().buildDiscountCardCreateDto();

        doReturn(expectedDiscountCard).when(discountCardRepository).create(discountCardToSave);
        when(discountCardConverter.convert(discountCardCreateDto)).thenReturn(discountCardToSave);

        discountCardService.create(discountCardCreateDto);

        verify(discountCardRepository).create(discountCardArgumentCaptor.capture());
        assertThat(discountCardArgumentCaptor.getValue()).hasFieldOrPropertyWithValue(DiscountCard.Fields.id, null);
    }

    @Test
    public void updateShouldCallsMergeAndSaveWhenDiscountCardFound() {
        var discountCardId = DiscountCardTestBuilder.builder().build().buildId();
        var discountCardUpdateDto = DiscountCardTestBuilder.builder().build().buildDiscountCardUpdateDto();
        var existingDiscountCard = DiscountCardTestBuilder.builder().build().buildDiscountCard();

        when(discountCardRepository.findById(discountCardId)).thenReturn(Optional.of(existingDiscountCard));

        discountCardService.update(discountCardId, discountCardUpdateDto);

        verify(discountCardRepository, times(1)).findById(discountCardId);
        verify(discountCardConverter, times(1)).merge(discountCardArgumentCaptor.capture(), eq(discountCardUpdateDto));
        assertSame(existingDiscountCard, discountCardArgumentCaptor.getValue());
    }

    @Test
    void updateShouldThrowCatNotFoundExceptionWhenCatNotFound() {
        var discountCardId = DiscountCardTestBuilder.builder().build().buildId();
        var discountCardUpdateDto = DiscountCardTestBuilder.builder().build().buildDiscountCardUpdateDto();

        when(discountCardRepository.findById(discountCardId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> discountCardService.update(discountCardId, discountCardUpdateDto));
        verify(discountCardRepository, times(1)).findById(discountCardId);
    }

    @Test
    void delete() {
        var discountCardId = DiscountCardTestBuilder.builder().build().buildId();

        discountCardService.delete(discountCardId);

        verify(discountCardRepository).delete(discountCardId);
    }
}