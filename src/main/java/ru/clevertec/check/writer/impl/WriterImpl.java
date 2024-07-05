package ru.clevertec.check.writer.impl;

import ru.clevertec.check.exception.WriterException;
import ru.clevertec.check.model.entity.Check;
import ru.clevertec.check.writer.Writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.clevertec.check.constant.Constant.*;

/**
 * Реализация записи чеков.
 */
public class WriterImpl implements Writer {

    private final Logger log = Logger.getLogger(WriterImpl.class.getName());

    /**
     * Запись чека в файл.
     *
     * @param check Чек.
     */
    public File writeCheck(Check check) {
        try {
            log.log(Level.INFO, "WriterImpl: generate check");
            FileWriter writer = new FileWriter(CHECK_CSV);

            // Запись даты и времени операции
            writer.append("DATE;TIME;\n");
            writer.append(check.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).append(";");
            writer.append(check.getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            writer.append("\n\n");

            // Записать списка товаров
            writer.append("QTY;DESCRIPTION;PRICE:DISCOUNT;TOTAL;\n");
            for (var item : check.getProductDataList()) {
                writer
                        .append(String.valueOf(item.getCount())).append(";")
                        .append(item.getProduct().getDescription()).append(";")
                        .append(String.valueOf(item.getProduct().getPrice())).append(CURRENCY).append(";")
                        .append(String.valueOf(item.getDiscount())).append(CURRENCY).append(";")
                        .append(String.valueOf(item.getTotalSum())).append(CURRENCY).append(";\n");
            }
            writer.append("\n");

            // Запись данных дисконтной карты, если она есть
            if (check.getDiscountCard() != null) {
                writer.append("DISCOUNT CARD;DISCOUNT PERCENTAGE;\n");
                writer.append(check.getDiscountCard().getNumber().toString()).append(";");
                writer.append(check.getDiscountCard().getAmount().toString()).append(PERCENT).append(";");
                writer.append("\n\n");
            }

            // Запись итоговой стоимости и скидки
            writer.append("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT;\n");
            writer
                    .append(String.valueOf(check.getTotalSum())).append(CURRENCY).append(";")
                    .append(String.valueOf(check.getTotalDiscount())).append(CURRENCY).append(";")
                    .append(String.valueOf(check.getTotalSumWithDiscount())).append(CURRENCY).append(";\n");
            writer.flush();
            writer.close();
            return new File(CHECK_CSV);
        } catch (IOException e) {
            log.log(Level.SEVERE, "WriterImpl: generate check error");
            throw new WriterException(INTERNAL_SERVER_ERROR);
        }
    }
}