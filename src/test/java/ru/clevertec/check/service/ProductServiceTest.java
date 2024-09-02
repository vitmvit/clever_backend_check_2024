package ru.clevertec.check.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.converter.ProductConverterImpl;
import ru.clevertec.check.exception.ConnectionException;
import ru.clevertec.check.exception.NotFoundException;
import ru.clevertec.check.model.entity.DiscountCard;
import ru.clevertec.check.model.entity.Product;
import ru.clevertec.check.repository.impl.ProductRepositoryImpl;
import ru.clevertec.check.service.impl.ProductServiceImpl;
import ru.clevertec.check.util.ProductTestBuilder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepositoryImpl productRepository;

    @Mock
    private ProductConverterImpl productConverter;

    @InjectMocks
    private ProductServiceImpl productService;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Test
    void findByIdShouldReturnExpectedProductWhenFound() {
        var expectedProduct = ProductTestBuilder.builder().build().buildProduct();
        var expectedProductDto = ProductTestBuilder.builder().build().buildProductDto();
        var productId = expectedProduct.getId();

        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));
        when(productConverter.convert(expectedProduct)).thenReturn(expectedProductDto);

        var actual = productService.findById(productId);

        assertEquals(expectedProductDto.getId(), actual.getId());
        assertEquals(expectedProductDto.getDescription(), actual.getDescription());
        assertEquals(expectedProductDto.getPrice(), actual.getPrice());
        assertEquals(expectedProductDto.getIsWholesale(), actual.getIsWholesale());
        assertEquals(expectedProductDto.getQuantity(), actual.getQuantity());
    }

    @Test
    void findByIdShouldThrowConnectionExceptionWhenNotFound() {
        doThrow(ConnectionException.class).when(productRepository).findById(Long.MAX_VALUE);

        var exception = assertThrows(ConnectionException.class, () -> productService.findById(Long.MAX_VALUE));
        assertEquals(exception.getClass(), ConnectionException.class);
    }

    @Test
    void createShouldReturnNewProduct() {
        var productToSave = ProductTestBuilder.builder().withId(null).build().buildProduct();
        var expectedProduct = ProductTestBuilder.builder().build().buildProduct();
        var productCreateDto = ProductTestBuilder.builder().withId(null).build().buildProductCreateDto();

        doReturn(expectedProduct).when(productRepository).create(productToSave);
        when(productConverter.convert(productCreateDto)).thenReturn(productToSave);

        productService.create(productCreateDto);

        verify(productRepository).create(productArgumentCaptor.capture());
        assertThat(productArgumentCaptor.getValue()).hasFieldOrPropertyWithValue(DiscountCard.Fields.id, null);
    }

    @Test
    public void updateShouldCallsMergeAndSaveWhenProductFound() {
        var productId = ProductTestBuilder.builder().build().buildId();
        var productUpdateDto = ProductTestBuilder.builder().build().buildProductUpdateDto();
        var existingProduct = ProductTestBuilder.builder().build().buildProduct();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        productService.update(productId, productUpdateDto);

        verify(productRepository, times(1)).findById(productId);
        verify(productConverter, times(1)).merge(productArgumentCaptor.capture(), eq(productUpdateDto));
        assertSame(existingProduct, productArgumentCaptor.getValue());
    }

    @Test
    void updateShouldThrowCatNotFoundExceptionWhenCatNotFound() {
        var productId = ProductTestBuilder.builder().build().buildId();
        var productUpdateDto = ProductTestBuilder.builder().build().buildProductUpdateDto();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.update(productId, productUpdateDto));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void delete() {
        var productId = ProductTestBuilder.builder().build().buildId();

        productRepository.delete(productId);

        verify(productRepository).delete(productId);
    }
}