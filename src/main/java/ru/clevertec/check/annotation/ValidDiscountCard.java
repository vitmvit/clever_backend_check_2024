package ru.clevertec.check.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для проверки валидности номера дисконтной карты.
 *
 * @Retention Указывает, что аннотация будет сохранена во время выполнения программы.
 * @Target Указывает, что аннотация может быть применена к полям.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidDiscountCard {

    String message() default "Invalid number card";
}
