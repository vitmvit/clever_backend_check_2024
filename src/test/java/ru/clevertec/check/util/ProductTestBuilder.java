package ru.clevertec.check.util;

import lombok.Builder;
import ru.clevertec.check.model.dto.ProductDto;
import ru.clevertec.check.model.dto.create.ProductCreateDto;
import ru.clevertec.check.model.dto.update.ProductUpdateDto;
import ru.clevertec.check.model.entity.Product;

import java.math.BigDecimal;

@Builder(setterPrefix = "with")
public class ProductTestBuilder {

    @Builder.Default
    private Long id = 111L;

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

    public ProductDto buildProductDto() {
        var product = new ProductDto();
        product.setId(id);
        product.setDescription(description);
        product.setPrice(price);
        product.setIsWholesale(wholesaleProduct);
        product.setQuantity(quantityInStock);
        return product;
    }

    public ProductUpdateDto buildProductUpdateDto() {
        var product = new ProductUpdateDto();
        product.setId(id);
        product.setDescription(description);
        product.setPrice(price);
        product.setIsWholesale(wholesaleProduct);
        product.setQuantity(quantityInStock);
        return product;
    }

    public ProductCreateDto buildProductCreateDto() {
        var product = new ProductCreateDto();
        product.setDescription(description);
        product.setPrice(price);
        product.setIsWholesale(wholesaleProduct);
        product.setQuantity(quantityInStock);
        return product;
    }
}
