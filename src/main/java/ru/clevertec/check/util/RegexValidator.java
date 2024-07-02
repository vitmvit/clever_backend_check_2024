package ru.clevertec.check.util;

import java.util.regex.Pattern;

import static ru.clevertec.check.constant.Constant.BAD_REQUEST;

/**
 * Валидатор на основе регулярных выражений.
 */
public class RegexValidator {

    /**
     * Проверить строку на соответствие регулярному выражению.
     *
     * @param input Входная строка.
     * @param regex Регулярное выражение.
     * @return {@code true} если строка соответствует регулярному выражению, иначе {@code false}.
     * @throws RuntimeException Если регулярное выражение некорректно.
     */
    public static boolean isValid(String input, String regex) {
        try {
            return Pattern.matches(regex, input);
        } catch (Exception e) {
            throw new RuntimeException(BAD_REQUEST);
        }
    }
}
