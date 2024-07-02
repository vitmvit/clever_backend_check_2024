package ru.clevertec.check.service.impl;

import ru.clevertec.check.model.Product;
import ru.clevertec.check.reader.ProductReader;
import ru.clevertec.check.reader.impl.ProductReaderImpl;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.writer.Writer;
import ru.clevertec.check.writer.impl.WriterImpl;

import static ru.clevertec.check.constant.Constant.BAD_REQUEST;

/**
 * Реализация сервиса продуктов.
 */
public class ProductServiceImpl implements ProductService {

    private final ProductReader productReader = new ProductReaderImpl();

    private final Writer writer = new WriterImpl();

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
        writer.writeError(new RuntimeException(BAD_REQUEST));
        throw new RuntimeException(BAD_REQUEST);
    }
}