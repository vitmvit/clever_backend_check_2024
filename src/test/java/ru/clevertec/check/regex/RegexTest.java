package ru.clevertec.check.regex;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.clevertec.check.util.RegexValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.clevertec.check.constant.Constant.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegexTest {

    @Test
    void validationRegexShouldReturnTrue() {
        assertTrue(RegexValidator.isValid("1-7 9-9 12-10 discountCard=3333 balanceDebitCard=10000 saveToFile=src/main/resources/saveToFile.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=root datasource.password=root", VALIDATION_REGEX));
        assertTrue(RegexValidator.isValid("1-7 9-9 12-10 balanceDebitCard=10000 saveToFile=src/main/resources/saveToFile.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=root datasource.password=root", VALIDATION_REGEX));
    }

    @Test
    void validationRegexShouldReturnFalse() {
        assertFalse(RegexValidator.isValid("1-7 9-9 12-10 discountCard=333 saveToFile=src/main/resources/saveToFile.csv datasource.url=jdbc:postgresql://localhost:5432/check datasource.username=root datasource.password=root", VALIDATION_REGEX));
    }

    @Test
    void productRegexShouldReturnTrue() {
        assertTrue(RegexValidator.isValid("1-7", PRODUCT_REGEX));
    }

    @Test
    void productRegexShouldReturnFalse() {
        assertFalse(RegexValidator.isValid("1,7", PRODUCT_REGEX));
        assertFalse(RegexValidator.isValid("17", PRODUCT_REGEX));
        assertFalse(RegexValidator.isValid("1 7", PRODUCT_REGEX));
    }

    @Test
    void productsRegexShouldReturnTrue() {
        assertTrue(RegexValidator.isValid("1-7 9-9 12-10", PRODUCTS_REGEX));
    }

    @Test
    void productsRegexShouldReturnFalse() {
        assertFalse(RegexValidator.isValid("1,7 9,9 12,10", PRODUCTS_REGEX));
    }

    @Test
    void discountCardRegexShouldReturnTrue() {
        assertTrue(RegexValidator.isValid("discountCard=3333", DISCOUNT_CARD_REGEX));
    }

    @Test
    void discountCardRegexShouldReturnFalse() {
        assertFalse(RegexValidator.isValid("discountCard 3333", DISCOUNT_CARD_REGEX));
    }

    @Test
    void balanceRegexShouldReturnTrue() {
        assertTrue(RegexValidator.isValid("balanceDebitCard=10000", BALANCE_REGEX));
        assertTrue(RegexValidator.isValid("balanceDebitCard=-10000", BALANCE_REGEX));
        assertTrue(RegexValidator.isValid("balanceDebitCard=10000.10", BALANCE_REGEX));
    }

    @Test
    void balanceRegexShouldReturnFalse() {
        assertFalse(RegexValidator.isValid("balanceDebitCard 10000", BALANCE_REGEX));
        assertFalse(RegexValidator.isValid("balanceDebitCard -10000.10", BALANCE_REGEX));
    }

    @Test
    void datasourceUrlShouldReturnTrue() {
        assertTrue(RegexValidator.isValid("datasource.url=jdbc:postgresql://localhost:5432/check", DATASOURCE_URL_REGEX));
    }

    @Test
    void datasourceUrlShouldReturnFalse() {
        assertFalse(RegexValidator.isValid("datasource.url=", DATASOURCE_URL_REGEX));
    }

    @Test
    void datasourceUsernameRegexShouldReturnTrue() {
        assertTrue(RegexValidator.isValid("datasource.username=root", DATASOURCE_USERNAME_REGEX));
    }

    @Test
    void datasourceUsernameRegexShouldReturnFalse() {
        assertFalse(RegexValidator.isValid("datasource.username=", DATASOURCE_USERNAME_REGEX));
    }

    @Test
    void datasourcePasswordRegexShouldReturnTrue() {
        assertTrue(RegexValidator.isValid("datasource.password=root", DATASOURCE_PASSWORD_REGEX));
    }

    @Test
    void datasourcePasswordRegexShouldReturnFalse() {
        assertFalse(RegexValidator.isValid("datasource.password=", DATASOURCE_PASSWORD_REGEX));
    }

    @Test
    void saveToFileRegexShouldReturnTrue() {
        assertTrue(RegexValidator.isValid("saveToFile=src/main/resources/saveToFile.csv", SAVE_TO_FILE_REGEX));
    }

    @Test
    void saveToFileRegexShouldReturnFalse() {
        assertFalse(RegexValidator.isValid("saveToFile=", SAVE_TO_FILE_REGEX));
    }
}
