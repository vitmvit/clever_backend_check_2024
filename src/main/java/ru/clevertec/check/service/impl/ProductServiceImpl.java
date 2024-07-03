package ru.clevertec.check.service.impl;

import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.repository.impl.ProductRepositoryImpl;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.writer.Writer;
import ru.clevertec.check.writer.impl.WriterImpl;

import static ru.clevertec.check.constant.Constant.BAD_REQUEST;

/**
 * Реализация сервиса продуктов.
 */
public class ProductServiceImpl implements ProductService {

    private final Writer writer = new WriterImpl();

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
            ProductRepository productRepository = new ProductRepositoryImpl(url, username, password);
            return productRepository.findById(id);
        } catch (Exception e) {
            writer.writeError(new RuntimeException(BAD_REQUEST));
            throw new RuntimeException(BAD_REQUEST);
        }
    }
}