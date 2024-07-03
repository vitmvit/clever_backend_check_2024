package ru.clevertec.check.connection;

import ru.clevertec.check.config.DatabaseConfig;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;

/**
 * Класс для создания и получения соединения с базой данных.
 */
public class DbConnection {

    /**
     * Создает соединение с базой данных.
     *
     * @param url      URL базы данных.
     * @param username Имя пользователя базы данных.
     * @param password Пароль базы данных.
     */
    public void createConnection(String url, String username, String password) {
        if (ConnectionSingleton.getInstance().getConnection().isEmpty()) {
            DatabaseConfig configReader = new DatabaseConfig();
            Map<String, String> configMap = configReader.createConfigMap(url, username, password);
            Connection connection = new DatabaseConnector().connection(
                    configMap.get("driver"),
                    configMap.get("url"),
                    configMap.get("username"),
                    configMap.get("password")
            );
            ConnectionSingleton.setInstance(connection == null ? Optional.empty() : Optional.of(connection));
        }
    }

    /**
     * Возвращает соединение с базой данных.
     *
     * @param url      URL базы данных.
     * @param username Имя пользователя базы данных.
     * @param password Пароль базы данных.
     * @return Соединение с базой данных.
     */
    public Optional<Connection> getConnection(String url, String username, String password) {
        if (ConnectionSingleton.getInstance().getConnection().isEmpty()) {
            createConnection(url, username, password);
        }
        return ConnectionSingleton.getInstance().getConnection();
    }
}
