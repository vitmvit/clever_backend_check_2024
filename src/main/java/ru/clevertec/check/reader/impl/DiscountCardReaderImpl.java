package ru.clevertec.check.reader.impl;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.reader.DiscountCardReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static ru.clevertec.check.constant.Constant.DISCOUNT_CARD_CSV;
import static ru.clevertec.check.constant.Constant.INTERNAL_SERVER_ERROR;

/**
 * Реализация интерфейса DiscountCardReader для чтения данных о дисконтных картах из CSV-файла.
 */
public class DiscountCardReaderImpl implements DiscountCardReader {

    /**
     * Считывает данные из источника и возвращает список объектов.
     *
     * @return список объектов.
     */
    @Override
    public List<DiscountCard> read() {
        try {
            List<DiscountCard> discountCardList = new ArrayList<>();
            BufferedReader file = new BufferedReader(new FileReader(DISCOUNT_CARD_CSV));
            file.readLine();
            while (file.ready()) {
                String[] cols = file.readLine().split(";");
                discountCardList.add(new DiscountCard(Long.parseLong(cols[0]), Integer.parseInt(cols[1]), Short.parseShort(cols[2])));
            }
            file.close();
            return discountCardList;
        } catch (Exception e) {
            throw new RuntimeException(INTERNAL_SERVER_ERROR);
        }
    }
}