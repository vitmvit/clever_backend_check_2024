package ru.clevertec.check.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Класс для установления соединения с базой данных.
 */
public class DatabaseConnector {

    /**
     * Устанавливает соединение с базой данных.
     *
     * @param driver   Драйвер базы данных.
     * @param url      URL базы данных.
     * @param username Имя пользователя базы данных.
     * @param password Пароль базы данных.
     * @return Соединение с базой данных.
     */
    public Connection connection(String driver, String url, String username, String password) {
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
            return DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            System.out.println("Невозможно установить соединение");
        }
        return null;
    }
}
