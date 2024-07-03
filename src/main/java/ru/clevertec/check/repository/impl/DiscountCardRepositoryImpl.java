package ru.clevertec.check.repository.impl;

import ru.clevertec.check.connection.DbConnection;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.repository.DiscountCardRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static ru.clevertec.check.constant.Constant.INTERNAL_SERVER_ERROR;

/**
 * Реализация репозитория для работы с карточками скидок.
 */
public class DiscountCardRepositoryImpl implements DiscountCardRepository {

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
}
