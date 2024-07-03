package ru.clevertec.check.repository.impl;

import ru.clevertec.check.connection.DbConnection;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.ProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static ru.clevertec.check.constant.Constant.INTERNAL_SERVER_ERROR;

/**
 * Реализация репозитория для работы с продуктами.
 */
public class ProductRepositoryImpl implements ProductRepository {

    /**
     * Поиск продукта по его идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return Найденный продукт или пустое значение, если продукт не найден.
     * @throws RuntimeException с сообщением "INTERNAL_SERVER_ERROR", если возникла ошибка при получении соединения
     *                          с базой данных или ошибка выполнения запроса к базе данных.
     */
    @Override
    public Product findById(Long id, String url, String username, String password) {
        Optional<Connection> connection = new DbConnection().getConnection(url, username, password);
        if (connection.isPresent()) {
            String sql = "SELECT id, description, price, quantity_in_stock, wholesale_product FROM product WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Product result = new Product();
                    result.setId(rs.getLong("id"));
                    result.setDescription(rs.getString("description"));
                    result.setPrice(rs.getBigDecimal("price"));
                    result.setQuantityInStock(rs.getInt("quantity_in_stock"));
                    result.setWholesaleProduct(rs.getBoolean("wholesale_product"));
                    return result;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        throw new RuntimeException(INTERNAL_SERVER_ERROR);
    }
}
