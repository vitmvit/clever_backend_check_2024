package ru.clevertec.check.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.clevertec.check.parser.impl.ArgsParserImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArgsParserTest {

    private final ArgsParser argsParser = new ArgsParserImpl();

    @Test
    void getCheckShouldReturnExpectedCheck() {
        var args = new String[]{"1-7", "9-9", "12-10", "discountCard=3333", "balanceDebitCard=10000", "saveToFile=src/main/resources/saveToFile.csv", "datasource.url=jdbc:postgresql://localhost:5432/check", "datasource.username=root", "datasource.password=root"};
        var actual = argsParser.getCheck(args);

        assertNotNull(actual);
        assertThatNoException().isThrownBy(() -> argsParser.getCheck(args));
    }

    @Test
    void getCheckShouldReturnThrowRuntimeExceptionWhenBalanceDebitCardNotFound() {
        var args = new String[]{"1-7", "9-9", "12-10", "discountCard=3333", "saveToFile=src/main/resources/saveToFile.csv", "datasource.url=jdbc:postgresql://localhost:5432/check", "datasource.username=root", "datasource.password=root"};
        var exception = assertThrows(Exception.class, () -> argsParser.getCheck(args));
        assertEquals(exception.getClass(), RuntimeException.class);
    }

    @Test
    void getCheckShouldReturnThrowRuntimeExceptionWhenSaveToFileNotFound() {
        var args = new String[]{"1-7", "9-9", "12-10", "discountCard=3333", "balanceDebitCard=10000", "datasource.url=jdbc:postgresql://localhost:5432/check", "datasource.username=root", "datasource.password=root"};
        var exception = assertThrows(Exception.class, () -> argsParser.getCheck(args));
        assertEquals(exception.getClass(), RuntimeException.class);
    }

    @Test
    void getCheckShouldReturnThrowRuntimeExceptionWhenInputStringIncorrect() {
        var args = new String[]{"1-7", "9-9", "12-10", "discountCard=3333", "balanceDebitCard=10000"};
        var exception = assertThrows(Exception.class, () -> argsParser.getCheck(args));
        assertEquals(exception.getClass(), RuntimeException.class);
    }
}
