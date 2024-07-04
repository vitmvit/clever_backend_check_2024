package ru.clevertec.check.service;

import ru.clevertec.check.model.DiscountCard;

public interface DiscountCardService {

    DiscountCard findById(Long id, String url, String username, String password);

    DiscountCard findByNumber(Integer number, String url, String username, String password);
}