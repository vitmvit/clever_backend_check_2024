package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import ru.clevertec.check.converter.ProductConverter;
import ru.clevertec.check.exception.NotFoundException;
import ru.clevertec.check.model.dto.ProductDto;
import ru.clevertec.check.model.dto.create.ProductCreateDto;
import ru.clevertec.check.model.dto.update.ProductUpdateDto;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.service.ProductService;

/**
 * Реализация сервиса продуктов.
 */
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    /**
     * Находит продукт по его идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return DTO продукта.
     */
    @Override
    public ProductDto findById(Long id) {
        return productConverter.convert(productRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    /**
     * Создает новый продукт.
     *
     * @param dto DTO продукта.
     * @return DTO созданного продукта.
     */
    @Override
    public ProductDto create(ProductCreateDto dto) {
        return productConverter.convert(productRepository.create(productConverter.convert(dto)));
    }

    /**
     * Обновляет продукт.
     *
     * @param id  обновляемого продукта.
     * @param dto DTO продукта.
     * @return DTO обновленного продукта.
     */
    @Override
    public ProductDto update(Long id, ProductUpdateDto dto) {
        var product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        return productConverter.convert(productConverter.merge(product, dto));
    }

    /**
     * Удаляет продукт.
     *
     * @param id Идентификатор продукта.
     */
    @Override
    public void delete(Long id) {
        productRepository.delete(id);
    }
}