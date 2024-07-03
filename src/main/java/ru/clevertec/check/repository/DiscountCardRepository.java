package ru.clevertec.check.repository;

import ru.clevertec.check.model.DiscountCard;

public interface DiscountCardRepository {

    DiscountCard findByNumber(Integer number, String url, String username, String password);
}
