package ru.clevertec.check.repository.impl;

import ru.clevertec.check.connection.DbConnection;
import ru.clevertec.check.exception.ConnectionException;
import ru.clevertec.check.exception.NotFoundException;
import ru.clevertec.check.model.entity.Product;
import ru.clevertec.check.repository.ProductRepository;

import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.clevertec.check.constant.Constant.INTERNAL_SERVER_ERROR;

/**
 * Реализация репозитория для работы с продуктами.
 */
public class ProductRepositoryImpl implements ProductRepository {

    private final Optional<Connection> connection;

    private final Logger log = Logger.getLogger(ProductRepositoryImpl.class.getName());

    public ProductRepositoryImpl() {
        this.connection = new DbConnection().getConnection();
    }

    /**
     * Ищет товар по его идентификатору.
     *
     * @param id идентификатор товара
     * @return товар, если он найден, или пустой Optional, если он не найден
     * @throws ConnectionException если соединение с базой данных не установлено
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
                log.log(Level.SEVERE, "ProductRepositoryImpl: not found card by id");
                return Optional.empty();
            }
        }
        log.log(Level.SEVERE, "ProductRepositoryImpl: connection error");
        throw new ConnectionException(INTERNAL_SERVER_ERROR);
    }

    /**
     * Создает новый товар.
     *
     * @param product товар
     * @return созданный товар
     * @throws ConnectionException если соединение с базой данных не установлено
     * @throws NotFoundException   если товар не найден после создания
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
                log.log(Level.SEVERE, "ProductRepositoryImpl: create error");
                throw new NotFoundException(INTERNAL_SERVER_ERROR);
            }
        }
        log.log(Level.SEVERE, "ProductRepositoryImpl: connection error");
        throw new ConnectionException(INTERNAL_SERVER_ERROR);
    }

    /**
     * Обновляет существующий товар.
     *
     * @param product товар
     * @return обновленный товар
     * @throws ConnectionException если соединение с базой данных не установлено
     * @throws NotFoundException   если товар не найден после обновления
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
                log.log(Level.SEVERE, "ProductRepositoryImpl: update error");
                throw new NotFoundException(INTERNAL_SERVER_ERROR);
            }
        }
        log.log(Level.SEVERE, "ProductRepositoryImpl: connection error");
        throw new ConnectionException(INTERNAL_SERVER_ERROR);
    }

    /**
     * Удаляет товар по его идентификатору.
     *
     * @param id идентификатор товара
     * @throws ConnectionException если соединение с базой данных не установлено
     * @throws NotFoundException   если товар не найден после удаления
     */
    @Override
    public void delete(Long id) {
        if (connection.isPresent()) {
            String sql = "DELETE FROM product WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            } catch (SQLException ex) {
                log.log(Level.SEVERE, "ProductRepositoryImpl: delete error");
                throw new NotFoundException(INTERNAL_SERVER_ERROR);
            }
        }
    }
}
