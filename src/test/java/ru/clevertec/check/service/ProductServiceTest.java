package ru.clevertec.check.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.exception.ConnectionException;
import ru.clevertec.check.service.impl.ProductServiceImpl;
import ru.clevertec.check.util.ProductTestBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductServiceImpl productService;

    @Test
    void findByIdShouldReturnExpectedProductWhenFound() {
        var expected = ProductTestBuilder.builder().build().buildProductDto();
        var id = expected.getId();

        when(productService.findById(id)).thenReturn(expected);
        var actual = productService.findById(id);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getIsWholesale(), actual.getIsWholesale());
        assertEquals(expected.getQuantity(), actual.getQuantity());
    }

    @Test
    void findByIdShouldThrowConnectionExceptionWhenNotFound() {
        doThrow(ConnectionException.class).when(productService).findById(Long.MAX_VALUE);

        var exception = assertThrows(ConnectionException.class, () -> productService.findById(Long.MAX_VALUE));
        assertEquals(exception.getClass(), ConnectionException.class);
    }

    @Test
    void createShouldReturnNewProduct() {
        var expected = ProductTestBuilder.builder().build().buildProductDto();
        var productToCreate = ProductTestBuilder.builder().withId(null).build().buildProductCreateDto();

        when(productService.create(productToCreate)).thenReturn(expected);
        var actual = productService.create(productToCreate);

        assertNotNull(actual.getId());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getIsWholesale(), actual.getIsWholesale());
        assertEquals(expected.getQuantity(), actual.getQuantity());
    }

    @Test
    public void updateShouldUpdatedProduct() {
        var id = ProductTestBuilder.builder().build().buildProduct().getId();
        var productToUpdate = ProductTestBuilder.builder().build().buildProductUpdateDto();
        var expected = ProductTestBuilder.builder().build().buildProductDto();

        when(productService.update(id, productToUpdate)).thenReturn(expected);
        var actual = productService.update(id, productToUpdate);

        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getIsWholesale(), actual.getIsWholesale());
        assertEquals(expected.getQuantity(), actual.getQuantity());
    }

    @Test
    void delete() {
        Long id = ProductTestBuilder.builder().build().buildProduct().getId();
        doNothing().when(productService).delete(id);

        assertDoesNotThrow(() -> productService.delete(id));
    }
}
