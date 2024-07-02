package ru.clevertec.check.reader;

import ru.clevertec.check.model.Product;

import java.util.List;

public interface ProductReader {

    List<Product> read(String pathToFile);
}