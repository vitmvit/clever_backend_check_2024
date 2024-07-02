package ru.clevertec.check.service;

import ru.clevertec.check.model.Product;

public interface ProductService {

    Product findById(Long id, String pathToFile);
}