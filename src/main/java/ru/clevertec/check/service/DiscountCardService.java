package ru.clevertec.check.service;

import ru.clevertec.check.model.DiscountCard;

public interface DiscountCardService {

    DiscountCard findByNumber(Short number, String url, String username, String password);
}