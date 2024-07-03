package ru.clevertec.check.repository;

import ru.clevertec.check.model.Product;

public interface ProductRepository {

    Product findById(Long id, String url, String username, String password);
}
