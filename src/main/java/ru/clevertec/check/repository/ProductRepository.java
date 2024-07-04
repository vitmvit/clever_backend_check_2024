package ru.clevertec.check.repository;

import ru.clevertec.check.model.Product;

public interface ProductRepository {

    Product findById(Long id, String url, String username, String password);

    Product create(Product product, String url, String username, String password);

    Product update(Product product, String url, String username, String password);

    void delete(Long id, String url, String username, String password);
}
