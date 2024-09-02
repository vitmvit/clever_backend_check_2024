package ru.clevertec.check.service;

import ru.clevertec.check.model.dto.ProductDto;
import ru.clevertec.check.model.dto.create.ProductCreateDto;
import ru.clevertec.check.model.dto.update.ProductUpdateDto;

public interface ProductService {

    ProductDto findById(Long id);

    ProductDto create(ProductCreateDto dto);

    ProductDto update(Long id, ProductUpdateDto dto);

    void delete(Long id);
}