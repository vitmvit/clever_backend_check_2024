package ru.clevertec.check.reader;

import ru.clevertec.check.model.DiscountCard;

import java.util.List;

public interface DiscountCardReader {

    List<DiscountCard> read();
}