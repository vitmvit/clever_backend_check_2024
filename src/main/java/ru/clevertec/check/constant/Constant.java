package ru.clevertec.check.constant;

/**
 * Класс для хранения констант, используемых в проекте.
 */
public class Constant {

    // driver
    public static final String DRIVER = "org.postgresql.Driver";

    // file path
    public static final String ERROR_CSV = "result.csv";

    // error
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
    public static final String BAD_REQUEST = "BAD REQUEST";
    public static final String NOT_ENOUGH_MONEY = "NOT ENOUGH MONEY";

    // regex
    public static final String VALIDATION_REGEX = "((\\d+[-]{1}\\d+\\s+){1,}(\\d+[-]{1}\\d+){1})(\\s{1}discountCard=\\d{4})?(\\s{1}balanceDebitCard=-?(\\d+)([.]\\d+{1,2})?){1}(\\s{1}(saveToFile=\\S{1,}))?\\s{1}(datasource.url=\\S{1,})\\s{1}(datasource.username=\\S{1,})\\s{1}(datasource.password=\\S{1,})";
    public static final String PRODUCT_REGEX = "(\\d+[-]{1}\\d+)";
    public static final String PRODUCTS_REGEX = "((\\d+[-]{1}\\d+\\s+){1,}(\\d+[-]{1}\\d+){1})";
    public static final String DISCOUNT_CARD_REGEX = "discountCard=(\\d{4})";
    public static final String BALANCE_REGEX = "balanceDebitCard=-?(\\d+)([.]\\d+{1,2})?";
    public static final String DATASOURCE_URL_REGEX = "datasource.url=(\\S{1,})";
    public static final String DATASOURCE_USERNAME_REGEX = "datasource.username=(\\S{1,})";
    public static final String DATASOURCE_PASSWORD_REGEX = "datasource.password=(\\S{1,})";
    public static final String SAVE_TO_FILE_REGEX = "saveToFile=(\\S{1,})";

    // symbols
    public static final String CURRENCY = "$";
    public static final String PERCENT = "%";

    public static final int DISCOUNT_SALE_PERCENT = 10;
    public static final int SCALE = 2;
    public static final int COUNT_WHOLESALE_PRODUCT = 5;
}