package ru.clevertec.check.repository.impl;

import ru.clevertec.check.connection.DbConnection;
import ru.clevertec.check.exception.ConnectionException;
import ru.clevertec.check.exception.NotFoundException;
import ru.clevertec.check.model.entity.Product;
import ru.clevertec.check.repository.ProductRepository;

import java.sql.*;
import java.util.Optional;

import static ru.clevertec.check.constant.Constant.INTERNAL_SERVER_ERROR;

/**
 * Реализация репозитория для работы с продуктами.
 */
public class ProductRepositoryImpl implements ProductRepository {

    private final Optional<Connection> connection;

    public ProductRepositoryImpl() {
        this.connection = new DbConnection().getConnection();
    }

    /**
     * Находит {@link Product} по его идентификатору.
     *
     * @param id Идентификатор продукта.
     * @return {@link Product}.
     * @throws NotFoundException   если продукт с заданным идентификатором не найден.
     * @throws ConnectionException если произошла ошибка при подключении к базе данных.
     */
    @Override
    public Optional<Product> findById(Long id) {
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
                    return Optional.of(result);
                }
            } catch (SQLException ex) {
                return Optional.empty();
            }
        }
        throw new ConnectionException(INTERNAL_SERVER_ERROR);
    }

    /**
     * Создает новый {@link Product}.
     *
     * @param product {@link Product}.
     * @return Созданный {@link Product}.
     * @throws NotFoundException   если продукт не был создан.
     * @throws ConnectionException если произошла ошибка при подключении к базе данных.
     */
    @Override
    public Product create(Product product) {
        if (connection.isPresent()) {
            String sql = "INSERT INTO product (description, price, quantity_in_stock, wholesale_product) VALUES (?,?,?,?)";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, product.getDescription());
                ps.setBigDecimal(2, product.getPrice());
                ps.setInt(3, product.getQuantityInStock());
                ps.setBoolean(4, product.getWholesaleProduct());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return findById(rs.getLong(1)).get();
                }
            } catch (SQLException ex) {
                throw new NotFoundException(INTERNAL_SERVER_ERROR);
            }
        }
        throw new ConnectionException(INTERNAL_SERVER_ERROR);
    }

    /**
     * Обновляет {@link Product}.
     *
     * @param product {@link Product}.
     * @return Обновленный {@link Product}.
     * @throws NotFoundException   если продукт не был обновлен.
     * @throws ConnectionException если произошла ошибка при подключении к базе данных.
     */
    @Override
    public Product update(Product product) {
        if (connection.isPresent()) {
            String sql = "UPDATE product SET description = ?, price = ?, quantity_in_stock = ?, wholesale_product = ? WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setString(1, product.getDescription());
                ps.setBigDecimal(2, product.getPrice());
                ps.setInt(3, product.getQuantityInStock());
                ps.setBoolean(4, product.getWholesaleProduct());
                ps.setLong(5, product.getId());
                ps.executeUpdate();
                return findById(product.getId()).get();
            } catch (SQLException ex) {
                throw new NotFoundException(INTERNAL_SERVER_ERROR);
            }
        }
        throw new ConnectionException(INTERNAL_SERVER_ERROR);
    }

    /**
     * Удаляет {@link Product}.
     *
     * @param id Идентификатор продукта.
     * @throws NotFoundException   если продукт с заданным идентификатором не был найден.
     * @throws ConnectionException если произошла ошибка при подключении к базе данных.
     */
    @Override
    public void delete(Long id) {
        if (connection.isPresent()) {
            String sql = "DELETE FROM product WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            } catch (SQLException ex) {
                throw new NotFoundException(INTERNAL_SERVER_ERROR);
            }
        }
    }
}
