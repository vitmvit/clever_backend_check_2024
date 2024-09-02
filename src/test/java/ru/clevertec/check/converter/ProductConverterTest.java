package ru.clevertec.check.converter;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.model.entity.Product;
import ru.clevertec.check.util.ProductTestBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductConverterTest {

    private final ProductConverter productConverter = new ProductConverterImpl();

    @Test
    public void productToProductDtoTest() {
        var expected = ProductTestBuilder.builder().build().buildProduct();

        var actual = productConverter.convert(expected);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getWholesaleProduct(), actual.getIsWholesale());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getQuantityInStock(), actual.getQuantity());
    }

    @Test
    public void productCreateDtoToProductTest() {
        var expected = ProductTestBuilder.builder().build().buildProduct();
        var toCreate = ProductTestBuilder.builder().build().buildProductCreateDto();

        var actual = productConverter.convert(toCreate);

        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.quantityInStock, expected.getQuantityInStock())
                .hasFieldOrPropertyWithValue(Product.Fields.wholesaleProduct, expected.getWholesaleProduct());
    }

    @Test
    public void mergeTest() {
        var expected = ProductTestBuilder.builder().build().buildProduct();
        var toUpdate = ProductTestBuilder.builder().build().buildProductUpdateDto();

        var actual = productConverter.merge(expected, toUpdate);

        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                .hasFieldOrPropertyWithValue(Product.Fields.quantityInStock, expected.getQuantityInStock())
                .hasFieldOrPropertyWithValue(Product.Fields.wholesaleProduct, expected.getWholesaleProduct());
    }
}
