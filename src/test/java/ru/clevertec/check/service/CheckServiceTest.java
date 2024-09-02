package ru.clevertec.check.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.check.exception.GenerateCheckException;
import ru.clevertec.check.model.dto.create.CheckCreateDto;
import ru.clevertec.check.model.entity.CheckProduct;
import ru.clevertec.check.model.entity.DiscountCard;
import ru.clevertec.check.model.entity.Product;
import ru.clevertec.check.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.check.repository.impl.ProductRepositoryImpl;
import ru.clevertec.check.service.impl.CheckServiceImpl;
import ru.clevertec.check.util.CheckTestBuilder;
import ru.clevertec.check.util.ProductTestBuilder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CheckServiceTest {

    @Mock
    private ProductRepositoryImpl productRepository;

    @Mock
    private DiscountCardRepositoryImpl discountCardRepository;

    @InjectMocks
    private CheckServiceImpl checkService;

    private CheckCreateDto check;
    private Product product;

    @BeforeEach
    void setUp() {
        var listProducts = List.of(new CheckProduct(2L, 2), new CheckProduct(6L, 3));
        check = CheckTestBuilder.builder().build().buildCheckCreateDto();
        product = ProductTestBuilder.builder().build().buildProduct();
        check.setProducts(listProducts);
    }

    @Test
    public void getCheckShouldReturnFile() {
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(discountCardRepository.findByNumber(any())).thenReturn(Optional.of(new DiscountCard(1L, 123, (short) 10)));

        var file = checkService.getCheck(check);

        assertTrue(file.isFile());
    }

    @Test
    public void getCheckShouldThrowConnectionExceptionWhenNotFoundProduct() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        var exception = assertThrows(GenerateCheckException.class, () -> checkService.getCheck(check));

        assertEquals(exception.getClass(), GenerateCheckException.class);
    }

    @Test
    public void getCheckShouldThrowConnectionExceptionWhenNotFoundDiscountCard() {
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(discountCardRepository.findByNumber(any())).thenReturn(Optional.empty());

        var exception = assertThrows(GenerateCheckException.class, () -> checkService.getCheck(check));

        assertEquals(exception.getClass(), GenerateCheckException.class);
    }
}