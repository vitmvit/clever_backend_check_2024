package ru.clevertec.check.config;

import java.util.HashMap;
import java.util.Map;

import static ru.clevertec.check.constant.Constant.DRIVER;

/**
 * Класс конфигурации базы данных.
 */
public class DatabaseConfig {

    /**
     * Создает карту конфигурации со значениями драйвера, URL, имени пользователя и пароля базы данных.
     *
     * @param url      URL базы данных.
     * @param username Имя пользователя базы данных.
     * @param password Пароль базы данных.
     * @return Карта конфигурации.
     */
    public Map<String, String> createConfigMap(String url, String username, String password) {
        Map<String, String> map = new HashMap<>(4);
        map.put("driver", DRIVER);
        map.put("url", url);
        map.put("username", username);
        map.put("password", password);
        return map;
    }
}