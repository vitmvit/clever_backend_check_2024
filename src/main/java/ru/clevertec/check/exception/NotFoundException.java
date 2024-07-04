package ru.clevertec.check.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        this("Not found exception");
    }

    public NotFoundException(String message) {
        super(message);
    }
}