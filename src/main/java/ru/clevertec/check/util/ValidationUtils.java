package ru.clevertec.check.util;

import ru.clevertec.check.exception.ArgumentException;

import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.clevertec.check.constant.Constant.BAD_REQUEST;

public class ValidationUtils {

    public static void validateDiscountCardNumber(Integer number) {
        if (number < 1000 || number > 9999) {
            Logger.getLogger(ValidationUtils.class.getName()).log(Level.SEVERE, "ValidationUtils: invalid card number");
            throw new ArgumentException(BAD_REQUEST);
        }
    }
}
