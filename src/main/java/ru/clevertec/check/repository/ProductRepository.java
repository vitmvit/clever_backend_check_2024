package ru.clevertec.check.repository;

import ru.clevertec.check.model.entity.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long id);

    Product create(Product product);

    Product update(Product product);

    void delete(Long id);
}
