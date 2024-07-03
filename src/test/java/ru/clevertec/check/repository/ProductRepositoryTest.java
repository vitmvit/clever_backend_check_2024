package ru.clevertec.check.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.impl.ProductRepositoryImpl;
import ru.clevertec.check.util.ProductTestBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.clevertec.check.util.Constant.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductRepositoryTest {

    private final ProductRepository productRepository = new ProductRepositoryImpl();

    @Test
    public void findByIdShouldReturnExpectedProduct() {
        var expected = ProductTestBuilder.builder().build().buildProduct();
        var actual = productRepository.findById(1L, CORRECT_URL, CORRECT_USERNAME, CORRECT_PASSWORD);

        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.id, expected.getId())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.quantityInStock, expected.getQuantityInStock())
                .hasFieldOrPropertyWithValue(Product.Fields.wholesaleProduct, expected.getWholesaleProduct());
    }

    @Test
    public void findByIdShouldThrowRuntimeExceptionWhenProductNotFound() {
        var exception = assertThrows(Exception.class, () -> productRepository.findById(100L, CORRECT_URL, CORRECT_USERNAME, CORRECT_PASSWORD));
        assertEquals(exception.getClass(), RuntimeException.class);
    }
}
