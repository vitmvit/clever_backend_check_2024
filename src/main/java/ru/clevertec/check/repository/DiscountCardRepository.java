package ru.clevertec.check.repository;

import ru.clevertec.check.model.entity.DiscountCard;

import java.util.Optional;

public interface DiscountCardRepository {

    Optional<DiscountCard> findById(Long id);

    Optional<DiscountCard> findByNumber(Integer number);

    DiscountCard create(DiscountCard discountCard);

    DiscountCard update(DiscountCard discountCard);

    void delete(Long id);

}
