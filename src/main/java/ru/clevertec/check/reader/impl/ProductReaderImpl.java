package ru.clevertec.check.reader.impl;

import ru.clevertec.check.model.Product;
import ru.clevertec.check.reader.ProductReader;
import ru.clevertec.check.writer.Writer;
import ru.clevertec.check.writer.impl.WriterImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static ru.clevertec.check.constant.Constant.INTERNAL_SERVER_ERROR;

/**
 * Реализация интерфейса ProductReader для чтения данных о продуктах из CSV-файла.
 */
public class ProductReaderImpl implements ProductReader {

    private final Writer writer = new WriterImpl();

    /**
     * Считывает данные из источника и возвращает список объектов.
     *
     * @return список объектов.
     */
    @Override
    public List<Product> read(String pathToFile) {
        try {
            List<Product> productList = new ArrayList<>();
            BufferedReader file = new BufferedReader(new FileReader(pathToFile));
            file.readLine();
            while (file.ready()) {
                String[] cols = file.readLine().split(";");
                productList.add(new Product(Long.parseLong(cols[0]), cols[1], new BigDecimal(cols[2]), Integer.parseInt(cols[3]), Boolean.parseBoolean(cols[4])));
            }
            file.close();
            return productList;
        } catch (Exception e) {
            writer.writeError(new RuntimeException(INTERNAL_SERVER_ERROR));
            throw new RuntimeException(INTERNAL_SERVER_ERROR);
        }
    }
}