package ru.clevertec.check.exception;

import static ru.clevertec.check.constant.Constant.BAD_REQUEST;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        this(BAD_REQUEST);
    }

    public NotFoundException(String message) {
        super(message);
    }
}