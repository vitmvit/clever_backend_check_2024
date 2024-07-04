//package ru.clevertec.check.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import ru.clevertec.check.model.entity.Product;
//import ru.clevertec.check.service.impl.ProductServiceImpl;
//import ru.clevertec.check.util.ProductTestBuilder;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class ProductServiceTest {
//
//    private final ProductService productService = new ProductServiceImpl();
//
//    @Test
//    void findByIdShouldReturnExpectedProductWhenFound() {
//        var expected = ProductTestBuilder.builder().build().buildProduct();
//        Long id = expected.getId();
//
//        var actual = productService.findById(id);
//
//        assertThat(actual)
//                .hasFieldOrPropertyWithValue(Product.Fields.id, expected.getId())
//                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
//                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
//                .hasFieldOrPropertyWithValue(Product.Fields.quantityInStock, expected.getQuantityInStock())
//                .hasFieldOrPropertyWithValue(Product.Fields.wholesaleProduct, expected.getWholesaleProduct());
//    }
//
//    @Test
//    public void findByIdShouldThrowRuntimeExceptionWhenProductNotFound() {
//        var exception = assertThrows(Exception.class, () -> productService.findById(100L));
//        assertEquals(exception.getClass(), RuntimeException.class);
//    }
//}
