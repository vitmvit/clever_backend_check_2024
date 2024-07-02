package ru.clevertec.check.service.impl;

import ru.clevertec.check.model.Product;
import ru.clevertec.check.reader.ProductReader;
import ru.clevertec.check.reader.impl.ProductReaderImpl;
import ru.clevertec.check.service.ProductService;

import static ru.clevertec.check.constant.Constant.BAD_REQUEST;

/**
 * Реализация сервиса продуктов.
 */
public class ProductServiceImpl implements ProductService {

    private final ProductReader productReader = new ProductReaderImpl();

    /**
     * Поиск продукта по идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return Продукт.
     * @throws RuntimeException Если продукт с указанным идентификатором не найден.
     */
    public Product findById(Long id, String pathToFile) {
        var productList = productReader.read(pathToFile);
        for (var item : productList) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        throw new RuntimeException(BAD_REQUEST);
    }
}