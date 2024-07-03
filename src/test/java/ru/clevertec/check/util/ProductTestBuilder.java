package ru.clevertec.check.util;

import lombok.Builder;
import ru.clevertec.check.model.Product;

import java.math.BigDecimal;

@Builder(setterPrefix = "with")
public class ProductTestBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String description = "Milk";

    @Builder.Default
    private BigDecimal price = new BigDecimal("1.07");

    @Builder.Default
    private Integer quantityInStock = 10;

    @Builder.Default
    private Boolean wholesaleProduct = true;

    public Product buildProduct() {
        return new Product(id, description, price, quantityInStock, wholesaleProduct);
    }
}
