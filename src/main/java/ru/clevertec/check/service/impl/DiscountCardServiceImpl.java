package ru.clevertec.check.service.impl;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.repository.DiscountCardRepository;
import ru.clevertec.check.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.check.service.DiscountCardService;
import ru.clevertec.check.writer.Writer;
import ru.clevertec.check.writer.impl.WriterImpl;

import static ru.clevertec.check.constant.Constant.BAD_REQUEST;

/**
 * Реализация сервиса дисконтных карт.
 */
public class DiscountCardServiceImpl implements DiscountCardService {

    private final Writer writer = new WriterImpl();
    private final DiscountCardRepository productRepository = new DiscountCardRepositoryImpl();

    /**
     * Найти карточку скидки по номеру
     *
     * @param number   номер карты
     * @param url      адрес БД
     * @param username имя пользователя для подключения к БД
     * @param password пароль для подключения к БД
     * @return объект карточки скидки
     */
    public DiscountCard findByNumber(Integer number, String url, String username, String password) {
        try {
            return productRepository.findByNumber(number, url, username, password);
        } catch (Exception e) {
            writer.writeError(new RuntimeException(BAD_REQUEST));
            throw new RuntimeException(BAD_REQUEST);
        }
    }
}