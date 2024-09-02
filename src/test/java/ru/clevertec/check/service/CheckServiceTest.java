package ru.clevertec.check.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.clevertec.check.exception.GenerateCheckException;
import ru.clevertec.check.model.entity.CheckProduct;
import ru.clevertec.check.service.impl.CheckServiceImpl;
import ru.clevertec.check.util.CheckTestBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckServiceTest {

    private final CheckService checkService = new CheckServiceImpl();

    @Test
    public void getCheckShouldReturnFile() {
        var listProducts = List.of(new CheckProduct(2L, 2), new CheckProduct(6L, 3));
        var check = CheckTestBuilder.builder().build().buildCheckCreateDto();
        check.setProducts(listProducts);

        var file = checkService.getCheck(check);
        assertTrue(file.isFile());
    }

    @Test
    public void getCheckShouldThrowConnectionExceptionWhenNotFoundProduct() {
        var listProducts = List.of(new CheckProduct(200L, 2));
        var check = CheckTestBuilder.builder().build().buildCheckCreateDto();
        check.setProducts(listProducts);

        var exception = assertThrows(Exception.class, () -> checkService.getCheck(check));

        assertEquals(exception.getClass(), GenerateCheckException.class);
    }

    @Test
    public void getCheckShouldThrowConnectionExceptionWhenNotFoundDiscountCard() {
        var listProducts = List.of(new CheckProduct(2L, 2), new CheckProduct(6L, 3));
        var check = CheckTestBuilder.builder().build().buildCheckCreateDto();
        check.setProducts(listProducts);
        check.setDiscountCard(0000);

        var exception = assertThrows(Exception.class, () -> checkService.getCheck(check));

        assertEquals(exception.getClass(), GenerateCheckException.class);
    }
}