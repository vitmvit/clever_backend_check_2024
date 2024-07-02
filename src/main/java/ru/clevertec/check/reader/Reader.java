package ru.clevertec.check.reader;

import java.util.List;

/**
 * Интерфейс для чтения данных из какого-либо источника.
 * Реализует паттерн проектирования Фабричный метод.
 *
 * @param <T> тип объектов, которые будут читаться.
 */
public interface Reader<T> {

    List<T> read();
}
