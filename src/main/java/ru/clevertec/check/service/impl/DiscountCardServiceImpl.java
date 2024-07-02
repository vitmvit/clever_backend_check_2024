package ru.clevertec.check.service.impl;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.reader.DiscountCardReader;
import ru.clevertec.check.reader.impl.DiscountCardReaderImpl;
import ru.clevertec.check.service.DiscountCardService;

import static ru.clevertec.check.constant.Constant.BAD_REQUEST;

/**
 * Реализация сервиса дисконтных карт.
 */
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardReader discountCardReader = new DiscountCardReaderImpl();

    /**
     * Поиск дисконтной карты по номеру.
     *
     * @param number Номер дисконтной карты.
     * @return Дисконтная карта.
     * @throws RuntimeException Если дисконтная карта с указанным номером не найдена.
     */
    public DiscountCard findByNumber(Short number) {
        var discountCardList = discountCardReader.read();
        for (var item : discountCardList) {
            if (item.getNumber().intValue() == number) {
                return item;
            }
        }
        throw new RuntimeException(BAD_REQUEST);
    }
}