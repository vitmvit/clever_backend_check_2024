package ru.clevertec.check.service.impl;

import ru.clevertec.check.model.Check;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.ProductData;
import ru.clevertec.check.service.CheckService;

import java.time.format.DateTimeFormatter;

/**
 * Реализация сервиса вывода чеков в консоль.
 */
public class CheckServiceImpl implements CheckService {

    /**
     * Вывод чека в консоль.
     *
     * @param check Чек.
     */
    @Override
    public void showCheck(Check check) {
        // Вывод даты и времени операции
        System.out.println("DATE: " + check.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        System.out.println("TIME: " + check.getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        System.out.println("+---------+--------------------------------+----------------+--------------+--------------+");
        System.out.println("|   QTY   |          DESCRIPTION           |     PRICE      |   DISCOUNT   |     TOTAL    |");
        System.out.println("+---------+--------------------------------+----------------+--------------+--------------+");

        // Вывод списка товаров
        for (ProductData item : check.getProductDataList()) {
            var product = item.getProduct();
            System.out.printf("| %7s | %-30s | %14s | %12s | %12s |\n",
                    item.getCount(),
                    product.getDescription(),
                    product.getPrice(),
                    item.getDiscount(),
                    item.getTotalSum()
            );
        }
        System.out.println("+---------+-------+------------------------+-------+--------+--------------+--------------+");

        // Вывести данные дисконтной карты, если она есть
        if (check.getDiscountCard() != null) {
            System.out.println("+-----------------+--------------------------------+");
            System.out.println("|  DISCOUNT CARD  | DISCOUNT PERCENTAGE            |");
            System.out.println("+-----------------+--------------------------------+");
            DiscountCard discountCard = check.getDiscountCard();
            System.out.printf("| %15s | %-30s |\n", discountCard.getNumber(), discountCard.getAmount());
            System.out.println("+-----------------+--------------------------------+");
        }

        // Вывести итоги
        System.out.println("+----------------+------------------+----------------------+");
        System.out.println("|    TOTAL PRICE |  TOTAL DISCOUNT  |  TOTAL WITH DISCOUNT |");
        System.out.println("+----------------+------------------+----------------------+");
        System.out.printf("| %14s | %16s | %20s |\n",
                check.getTotalSum(),
                check.getTotalDiscount(),
                check.getTotalSumWithDiscount()
        );
        System.out.println("+----------------+------------------+----------------------+");
    }
}
