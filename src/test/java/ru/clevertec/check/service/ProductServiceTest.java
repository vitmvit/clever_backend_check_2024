//package ru.clevertec.check.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//import ru.clevertec.check.converter.ProductConverter;
//import ru.clevertec.check.converter.ProductConverterImpl;
//import ru.clevertec.check.exception.ConnectionException;
//import ru.clevertec.check.model.dto.ProductDto;
//import ru.clevertec.check.repository.ProductRepository;
//import ru.clevertec.check.repository.impl.ProductRepositoryImpl;
//import ru.clevertec.check.service.impl.ProductServiceImpl;
//import ru.clevertec.check.util.ProductTestBuilder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ProductServiceTest {
//
//@Mock
//    private ProductRepository productRepository;
//
//    @Mock
//    private ProductConverter productConverter;
//
//@InjectMocks
//    private ProductServiceImpl productService;
//
//    @Test
//    void findByIdShouldReturnExpectedProductWhenFound() {
//        var expected = ProductTestBuilder.builder().build().buildProduct();
//        var productDto = ProductTestBuilder.builder().build().buildProductDto();
//        var id = expected.getId();
//
//        doReturn(Optional.of(expected)).when(productRepository).findById(id);
////        doReturn(expected).when(productService).findById(id);
//        doReturn(productDto).when(productConverter).convert(expected);
////        when(productConverter.convert(expected)).thenReturn(productDto);
//        var actual = productService.findById(id);
//
//        assertEquals(expected.getId(), actual.getId());
//        assertEquals(expected.getDescription(), actual.getDescription());
//        assertEquals(expected.getPrice(), actual.getPrice());
//        assertEquals(expected.getWholesaleProduct(), actual.getIsWholesale());
//        assertEquals(expected.getQuantityInStock(), actual.getQuantity());
//    }
//
//    @Test
//    void findByIdShouldThrowProductNotFoundExceptionWhenNotFound() {
//        var exception = assertThrows(Exception.class, () -> productService.findById(Long.MAX_VALUE));
//        assertEquals(exception.getClass(), ConnectionException.class);
//    }
//
//    @Test
//    void createShouldReturnNewProduct() {
//        var productToSave = ProductTestBuilder.builder().withId(null).build().buildProduct();
//        var expected = ProductTestBuilder.builder().build().buildProduct();
//        var productDto = ProductTestBuilder.builder().withId(null).build().buildProductCreateDto();
//
//        when(productRepository.create(productToSave)).thenReturn(expected);
//        when(productConverter.convert(productDto)).thenReturn(productToSave);
//        var actual = productService.create(productDto);
//
//        assertNotNull(actual.getId());
//        assertEquals(expected.getDescription(), actual.getDescription());
//        assertEquals(expected.getPrice(), actual.getPrice());
//        assertEquals(expected.getWholesaleProduct(), actual.getIsWholesale());
//        assertEquals(expected.getQuantityInStock(), actual.getQuantity());
//    }
//
//    @Test
//    public void updateShouldCallsMergeAndSaveWhenProductFound() {
//        var id = ProductTestBuilder.builder().build().buildProduct().getId();
//        var productUpdateDto = ProductTestBuilder.builder().build().buildProductUpdateDto();
//        var expected = ProductTestBuilder.builder().build().buildProduct();
//
//        when(productRepository.findById(id)).thenReturn(Optional.of(expected));
//        when(productConverter.convert(productUpdateDto)).thenReturn(expected);
//        var actual = productService.update(id, productUpdateDto);
//
//        assertEquals(expected.getDescription(), actual.getDescription());
//        assertEquals(expected.getPrice(), actual.getPrice());
//        assertEquals(expected.getWholesaleProduct(), actual.getIsWholesale());
//        assertEquals(expected.getQuantityInStock(), actual.getQuantity());
//    }
//
//    @Test
//    void delete() {
//        Long id = ProductTestBuilder.builder().build().buildProduct().getId();
//        doAnswer((i) -> {
//            productService.delete(id);
//            assertSame(id, i.getArgument(0));
//            return null;
//        }).when(productRepository).delete(id);
//    }
//}
