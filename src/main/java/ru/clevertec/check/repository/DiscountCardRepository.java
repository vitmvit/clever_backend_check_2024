package ru.clevertec.check.repository;

import ru.clevertec.check.model.DiscountCard;

public interface DiscountCardRepository {

    DiscountCard findById(Long id, String url, String username, String password);

    DiscountCard findByNumber(Integer number, String url, String username, String password);

    DiscountCard create(DiscountCard discountCard, String url, String username, String password);

    DiscountCard update(DiscountCard discountCard, String url, String username, String password);

    void delete(Long id, String url, String username, String password);

}
