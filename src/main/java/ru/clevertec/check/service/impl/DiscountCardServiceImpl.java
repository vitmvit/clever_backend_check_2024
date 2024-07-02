package ru.clevertec.check.service.impl;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.reader.Reader;
import ru.clevertec.check.reader.impl.DiscountCardReaderImpl;
import ru.clevertec.check.service.DiscountCardService;
import ru.clevertec.check.writer.Writer;
import ru.clevertec.check.writer.impl.WriterImpl;

import static ru.clevertec.check.constant.Constant.BAD_REQUEST;

/**
 * Реализация сервиса дисконтных карт.
 */
public class DiscountCardServiceImpl implements DiscountCardService {

    private final Reader<DiscountCard> discountCardReader = new DiscountCardReaderImpl();
    private final Writer writer = new WriterImpl();

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
        writer.writeError(new RuntimeException(BAD_REQUEST));
        throw new RuntimeException(BAD_REQUEST);
    }
}
