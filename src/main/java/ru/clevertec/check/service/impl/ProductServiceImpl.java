package ru.clevertec.check.service.impl;

import ru.clevertec.check.exception.NotFoundException;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.repository.impl.ProductRepositoryImpl;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.writer.Writer;
import ru.clevertec.check.writer.impl.WriterImpl;

import static ru.clevertec.check.constant.Constant.INTERNAL_SERVER_ERROR;

/**
 * Реализация сервиса продуктов.
 */
public class ProductServiceImpl implements ProductService {

    private final Writer writer = new WriterImpl();
    private final ProductRepository productRepository = new ProductRepositoryImpl();

    /**
     * Найти продукт по идентификатору
     *
     * @param id       идентификатор продукта
     * @param url      адрес БД
     * @param username имя пользователя для подключения к БД
     * @param password пароль для подключения к БД
     * @return объект продукта
     */
    public Product findById(Long id, String url, String username, String password) {
        try {
            return productRepository.findById(id, url, username, password);
        } catch (Exception e) {
            writer.writeError(new NotFoundException(INTERNAL_SERVER_ERROR));
            throw new NotFoundException(INTERNAL_SERVER_ERROR);
        }
    }
}