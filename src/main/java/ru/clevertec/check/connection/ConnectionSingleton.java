package ru.clevertec.check.connection;

import java.sql.Connection;
import java.util.Optional;

/**
 * Синглтон для хранения подключения к базе данных.
 */
public class ConnectionSingleton {

    private static volatile ConnectionSingleton instance;
    private final Optional<Connection> connectionOptional;

    private ConnectionSingleton(Optional<Connection> connectionOptional) {
        this.connectionOptional = connectionOptional;
    }

    /**
     * Возвращает экземпляр синглтона.
     *
     * @return Экземпляр синглтона.
     */
    public static ConnectionSingleton getInstance() {
        return setInstance(Optional.empty());
    }

    /**
     * Устанавливает экземпляр синглтона и возвращает его.
     *
     * @param value Подключение к базе данных.
     * @return Экземпляр синглтона.
     */
    public static ConnectionSingleton setInstance(Optional<Connection> value) {
        synchronized (ConnectionSingleton.class) {
            if (instance == null) {
                instance = new ConnectionSingleton(Optional.empty());
            }
            if (value.isPresent()) {
                instance = new ConnectionSingleton(value);
            }
        }
        return instance;
    }

    /**
     * Возвращает подключение к базе данных.
     *
     * @return Подключение к базе данных.
     */
    public Optional<Connection> getConnection() {
        return connectionOptional;
    }
}
