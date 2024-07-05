package ru.clevertec.check.repository.impl;

import ru.clevertec.check.connection.DbConnection;
import ru.clevertec.check.exception.ConnectionException;
import ru.clevertec.check.exception.NotFoundException;
import ru.clevertec.check.model.entity.DiscountCard;
import ru.clevertec.check.repository.DiscountCardRepository;

import java.sql.*;
import java.util.Optional;

import static ru.clevertec.check.constant.Constant.INTERNAL_SERVER_ERROR;

/**
 * Реализация репозитория для работы с карточками скидок.
 */
public class DiscountCardRepositoryImpl implements DiscountCardRepository {

    private final Optional<Connection> connection;

    public DiscountCardRepositoryImpl() {
        this.connection = new DbConnection().getConnection();
    }

    /**
     * Ищет карту скидок по ее идентификатору.
     *
     * @param id идентификатор карты скидки
     * @return карта скидки, если она найдена, или пустой Optional, если она не найдена
     * @throws ConnectionException если соединение с базой данных не установлено
     */
    @Override
    public Optional<DiscountCard> findById(Long id) {
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
                    return Optional.of(result);
                }
            } catch (SQLException ex) {
                return Optional.empty();
            }
        }
        throw new ConnectionException(INTERNAL_SERVER_ERROR);
    }

    /**
     * Ищет карту скидок по ее номеру.
     *
     * @param number номер карты скидки
     * @return карта скидки, если она найдена, или пустой Optional, если она не найдена
     * @throws ConnectionException если соединение с базой данных не установлено
     */
    @Override
    public Optional<DiscountCard> findByNumber(Integer number) {
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
                    return Optional.of(result);
                }
            } catch (SQLException ex) {
                return Optional.empty();
            }
        }
        throw new ConnectionException(INTERNAL_SERVER_ERROR);
    }

    /**
     * Создает новую карту скидки.
     *
     * @param discountCard карта скидки
     * @return созданная карта скидки
     * @throws ConnectionException если соединение с базой данных не установлено
     * @throws NotFoundException   если карта скидки не найдена после создания
     */
    @Override
    public DiscountCard create(DiscountCard discountCard) {
        if (connection.isPresent()) {
            String sql = "INSERT INTO discount_card (number, amount) VALUES (?,?)";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, discountCard.getNumber());
                ps.setShort(2, discountCard.getAmount());
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
     * Обновляет существующую карту скидки.
     *
     * @param discountCard карта скидки
     * @return обновленная карта скидки
     * @throws ConnectionException если соединение с базой данных не установлено
     * @throws NotFoundException   если карта скидки не найдена после обновления
     */
    @Override
    public DiscountCard update(DiscountCard discountCard) {
        if (connection.isPresent()) {
            String sql = "UPDATE discount_card SET number = ?, amount = ? WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql)) {
                ps.setInt(1, discountCard.getNumber());
                ps.setShort(2, discountCard.getAmount());
                ps.setLong(3, discountCard.getId());
                ps.executeUpdate();
                return findById(discountCard.getId()).get();
            } catch (SQLException ex) {
                throw new NotFoundException(INTERNAL_SERVER_ERROR);
            }
        }
        throw new ConnectionException(INTERNAL_SERVER_ERROR);
    }

    /**
     * Удаляет карту скидки по ее идентификатору.
     *
     * @param id идентификатор карты скидки
     * @throws ConnectionException если соединение с базой данных не установлено
     * @throws NotFoundException если карта скидки не найдена после удаления
     */
    @Override
    public void delete(Long id) {
        if (connection.isPresent()) {
            String sql = "DELETE FROM discount_card WHERE id = ?";
            try (PreparedStatement ps = connection.get().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            } catch (SQLException ex) {
                throw new NotFoundException(INTERNAL_SERVER_ERROR);
            }
        }
    }
}
