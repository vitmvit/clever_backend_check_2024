package ru.clevertec.check.util;

import java.util.regex.Pattern;

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
     */
    public static boolean isValid(String input, String regex) {
        return Pattern.matches(regex, input);
    }
}
