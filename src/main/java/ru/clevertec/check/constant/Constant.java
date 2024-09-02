package ru.clevertec.check.constant;

/**
 * Класс для хранения констант, используемых в проекте.
 */
public class Constant {

    public static final String SOLUTION_CONFIG = "application.yml";

    // file path
    public static final String CHECK_CSV = "result.csv";

    // error
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
    public static final String BAD_REQUEST = "BAD REQUEST";
    public static final String NOT_ENOUGH_MONEY = "NOT ENOUGH MONEY";

    // symbols
    public static final String CURRENCY = "$";
    public static final String PERCENT = "%";

    public static final int DISCOUNT_SALE_PERCENT = 10;
    public static final int SCALE = 2;
    public static final int COUNT_WHOLESALE_PRODUCT = 5;
}