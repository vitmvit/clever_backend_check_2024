//package ru.clevertec.check.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.clevertec.check.converter.DiscountCardConverter;
//import ru.clevertec.check.model.dto.ProductDto;
//import ru.clevertec.check.model.entity.Product;
//import ru.clevertec.check.repository.DiscountCardRepository;
//import ru.clevertec.check.service.impl.DiscountCardServiceImpl;
//import ru.clevertec.check.util.ProductTestBuilder;
//
//import java.util.Optional;
//import java.util.stream.Stream;
//
//@ExtendWith(MockitoExtension.class)
//public class DiscountCardServiceTest {
//
//    @Mock
//    private DiscountCardRepository discountCardRepository;
//
//    @Mock
//    private DiscountCardConverter discountCardConverter;
//
//    @InjectMocks
//    private final DiscountCardService discountCardService = new DiscountCardServiceImpl();
//
//    @Test
//    public void findByIdShouldReturnExpectedDiscountCard() {
//        Product expected = ProductTestBuilder.builder().build().buildProduct();
//        ProductDto infoProductDto = ProductTestBuilder.builder().build().buildProductDto();
//        Long id = expected.getId();
//        // when
//        when(productRepository.findById(id)).thenReturn(Optional.of(expected));
//        when(productMapper.toInfoProductDto(expected)).thenReturn(infoProductDto);
//        InfoProductDto actual = productService.get(id);
//        // then
//        assertEquals(expected.getUuid(), actual.id());
//        assertEquals(expected.getName(), actual.name());
//        assertEquals(expected.getDescription(), actual.description());
//        assertEquals(expected.getPrice(), actual.price());
//    }
//
//
//}
