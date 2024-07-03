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

    /**
     * Найти карточку скидки по номеру
     *
     * @param number   номер карты
     * @param url      адрес БД
     * @param username имя пользователя для подключения к БД
     * @param password пароль для подключения к БД
     * @return объект карточки скидки
     */
    public DiscountCard findByNumber(Short number, String url, String username, String password) {
        try {
            DiscountCardRepository productRepository = new DiscountCardRepositoryImpl(url, username, password);
            return productRepository.findByNumber(number);
        } catch (Exception e) {
            writer.writeError(new RuntimeException(BAD_REQUEST));
            throw new RuntimeException(BAD_REQUEST);
        }
    }
}