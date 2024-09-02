package ru.clevertec.check.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.exception.ConnectionException;
import ru.clevertec.check.model.entity.Product;
import ru.clevertec.check.repository.impl.ProductRepositoryImpl;
import ru.clevertec.check.util.ProductTestBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoryTest {

    private final ProductRepository productRepository = new ProductRepositoryImpl();

    private void deleteData(Long id) {
        productRepository.delete(id);
    }

    @Test
    public void findByIdShouldReturnExpectedProduct() {
        var toCreate = ProductTestBuilder.builder().build().buildProduct();
        var created = productRepository.create(toCreate);
        var actual = productRepository.findById(created.getId());

        assertThat(actual.get())
                .hasFieldOrPropertyWithValue(Product.Fields.description, toCreate.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, toCreate.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.wholesaleProduct, toCreate.getWholesaleProduct())
                .hasFieldOrPropertyWithValue(Product.Fields.quantityInStock, toCreate.getQuantityInStock());

        deleteData(actual.get().getId());
    }

    @Test
    public void findByIdShouldThrowExceptionWhenProductNotFound() {
        var exception = assertThrows(Exception.class, () -> productRepository.findById(Long.MAX_VALUE));
        assertEquals(exception.getClass(), ConnectionException.class);
    }

    @Test
    public void createShouldReturnNewProduct() {
        var toCreate = ProductTestBuilder.builder().build().buildProduct();
        var created = productRepository.create(toCreate);

        var actual = productRepository.findById(created.getId());

        assertNotNull(actual.get());
        assertThat(actual.get())
                .hasFieldOrPropertyWithValue(Product.Fields.description, toCreate.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, toCreate.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.wholesaleProduct, toCreate.getWholesaleProduct())
                .hasFieldOrPropertyWithValue(Product.Fields.quantityInStock, toCreate.getQuantityInStock());

        deleteData(created.getId());
    }

    @Test
    public void updateShouldReturnUpdatedProduct() {
        var toCreate = ProductTestBuilder.builder().build().buildProduct();
        var created = productRepository.create(toCreate);

        created.setQuantityInStock(1000);

        var actual = productRepository.update(created);

        Assertions.assertEquals(created.getId(), actual.getId());

        deleteData(created.getId());
    }

    @Test
    public void updateShouldReturnThrowExceptionWhenProductNotFound() {
        var expected = ProductTestBuilder.builder().build().buildProduct();

        expected.setId(Long.MAX_VALUE);

        var exception = assertThrows(ConnectionException.class, () -> productRepository.update(expected));
        assertEquals(exception.getClass(), ConnectionException.class);
    }

    @Test
    public void delete() {
        var toCreate = ProductTestBuilder.builder().build().buildProduct();
        var created = productRepository.create(toCreate);

        productRepository.delete(created.getId());

        var exception = assertThrows(Exception.class, () -> productRepository.findById(created.getId()));
        assertEquals(exception.getClass(), ConnectionException.class);
    }
}