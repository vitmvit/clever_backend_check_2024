package ru.clevertec.check.repository.impl;

import ru.clevertec.check.connection.DbConnection;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.repository.DiscountCardRepository;

import java.sql.*;
import java.util.Optional;

import static ru.clevertec.check.constant.Constant.INTERNAL_SERVER_ERROR;

/**
 * Реализация репозитория для работы с карточками скидок.
 */
public class DiscountCardRepositoryImpl implements DiscountCardRepository {

    @Override
    public DiscountCard findById(Long id, String url, String username, String password) {
        Optional<Connection> connection = new DbConnection().getConnection(url, username, password);
        if (connection.isPresent()) {
            String sql = "SELECT id, number, amount FROM discount_card WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    DiscountCard result = new DiscountCard();
                    result.setId(rs.getLong("id"));
                    result.setNumber(rs.getInt("number"));
                    result.setAmount(rs.getShort("amount"));
                    return result;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        throw new RuntimeException(INTERNAL_SERVER_ERROR);
    }

    /**
     * Поиск карточки скидок по номеру.
     *
     * @param number Номер карты клиента.
     * @return DiscountCard Объект карточки скидок.
     */
    @Override
    public DiscountCard findByNumber(Integer number, String url, String username, String password) {
        Optional<Connection> connection = new DbConnection().getConnection(url, username, password);
        if (connection.isPresent()) {
            String sql = "SELECT id, number, amount FROM discount_card WHERE number = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setInt(1, number);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    DiscountCard result = new DiscountCard();
                    result.setId(rs.getLong("id"));
                    result.setNumber(rs.getInt("number"));
                    result.setAmount(rs.getShort("amount"));
                    return result;
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        throw new RuntimeException(INTERNAL_SERVER_ERROR);
    }

    @Override
    public DiscountCard create(DiscountCard discountCard, String url, String username, String password) {
        Optional<Connection> connection = new DbConnection().getConnection(url, username, password);
        if (connection.isPresent()) {
            String sql = "INSERT INTO discount_card (number, amount) VALUES (?,?)";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, discountCard.getNumber());
                ps.setShort(2, discountCard.getAmount());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return findById(rs.getLong(1), url, username, password);
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Account not found");
            }
        }
        throw new RuntimeException(INTERNAL_SERVER_ERROR);
    }

    @Override
    public DiscountCard update(DiscountCard discountCard, String url, String username, String password) {
        Optional<Connection> connection = new DbConnection().getConnection(url, username, password);
        if (connection.isPresent()) {
            String sql = "UPDATE discount_card SET number = ?, amount = ? WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setInt(1, discountCard.getNumber());
                ps.setShort(2, discountCard.getAmount());
                ps.setLong(3, discountCard.getId());
                ps.executeUpdate();
                return findById(discountCard.getId(), url, username, password);
            } catch (SQLException ex) {
                System.out.println("Error query");
            }
        }
        throw new RuntimeException(INTERNAL_SERVER_ERROR);
    }

    @Override
    public void delete(Long id, String url, String username, String password) {
        Optional<Connection> connection = new DbConnection().getConnection(url, username, password);
        if (connection.isPresent()) {
            String sql = "DELETE FROM discount_card WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error query");
            }
        }
    }
}
