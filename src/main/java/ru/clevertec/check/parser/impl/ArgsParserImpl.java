package ru.clevertec.check.parser.impl;

import ru.clevertec.check.model.Check;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.model.ProductData;
import ru.clevertec.check.parser.ArgsParser;
import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.service.DiscountCardService;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.service.impl.CheckServiceImpl;
import ru.clevertec.check.service.impl.DiscountCardServiceImpl;
import ru.clevertec.check.service.impl.ProductServiceImpl;
import ru.clevertec.check.util.RegexValidator;
import ru.clevertec.check.writer.Writer;
import ru.clevertec.check.writer.impl.WriterImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static ru.clevertec.check.constant.Constant.*;

/**
 * Реализация парсера аргументов.
 */
public class ArgsParserImpl implements ArgsParser {

    private final ProductService productService = new ProductServiceImpl();
    private final DiscountCardService discountCardService = new DiscountCardServiceImpl();
    private final Writer writer = new WriterImpl();
    private final CheckService checkService = new CheckServiceImpl();

    /**
     * Создание чека.
     *
     * @param args Аргументы командной строки.
     * @return Сгенерированный чек.
     */
    public Check getCheck(String[] args) {
        var inputString = arrayToString(args);
        // Проверить валидность входной строки
        if (RegexValidator.isValid(inputString, VALIDATION_REGEX)) {
            var pathToFile = getPathToFile(inputString);
            var saveToFile = getSaveToFile(inputString);
            if (pathToFile != null || saveToFile != null) {
                if (pathToFile == null) {
                    writer.writeError(new RuntimeException(BAD_REQUEST), saveToFile);
                    throw new RuntimeException(BAD_REQUEST);
                }
                if (saveToFile == null) {
                    writer.writeError(new RuntimeException(BAD_REQUEST));
                    throw new RuntimeException(BAD_REQUEST);
                }
            } else {
                writer.writeError(new RuntimeException(BAD_REQUEST));
                throw new RuntimeException(BAD_REQUEST);
            }
            var productCountList = extractProductCount(inputString, pathToFile);

            // Рассчитать общую сумму
            var totalSum = calculateTotalSum(productCountList);

            // Рассчитать общую скидку
            var totalDiscount = calculateTotalDiscount(productCountList);

            // Рассчитать общую сумму с учетом скидки
            var totalSumWithDiscount = totalSum.subtract(totalDiscount);

            // Получить дисконтную карту, если она есть
            var discountCard = getDiscountCard(inputString).orElse(null);
            var balance = getBalance(inputString);
            if (balance.compareTo(totalSumWithDiscount) > 0) {
                var check = new Check(LocalDateTime.now(), productCountList, discountCard, totalSum, totalDiscount, totalSumWithDiscount);
                writer.writeCheck(check, saveToFile);
                checkService.showCheck(check);
                return check;
            } else {
                writer.writeError(new RuntimeException(NOT_ENOUGH_MONEY));
                throw new RuntimeException(NOT_ENOUGH_MONEY);
            }
        } else {
            writer.writeError(new RuntimeException(BAD_REQUEST));
            throw new RuntimeException(BAD_REQUEST);
        }
    }

    /**
     * Получение дисконтной карты.
     *
     * @param input Входная строка.
     * @return Дисконтная карта.
     */
    private Optional<DiscountCard> getDiscountCard(String input) {
        Pattern pattern = Pattern.compile(DISCOUNT_CARD_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return Optional.of(discountCardService.findByNumber(Short.parseShort(matcher.group(1))));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Получение баланса.
     *
     * @param input Входная строка.
     * @return Баланс.
     */
    private BigDecimal getBalance(String input) {
        Pattern pattern = Pattern.compile(BALANCE_REGEX);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return new BigDecimal(matcher.group(1));
        } else {
            writer.writeError(new RuntimeException(INTERNAL_SERVER_ERROR));
            throw new RuntimeException(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Получение пути к файлу, откуда считывать данные.
     *
     * @param input Входная строка.
     * @return Баланс.
     */
    private String getPathToFile(String input) {
        Pattern pattern = Pattern.compile(PATH_TO_FILE_REGEX);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    /**
     * Получение пути к файлу, куда записывать данные.
     *
     * @param input Входная строка.
     * @return Баланс.
     */
    private String getSaveToFile(String input) {
        Pattern pattern = Pattern.compile(SAVE_TO_FILE_REGEX);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    /**
     * Расчет общей суммы.
     *
     * @param list Список товаров и их количества.
     * @return Общая сумма.
     */
    private BigDecimal calculateTotalSum(List<ProductData> list) {
        return list.stream()
                .map(ProductData::getTotalSum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Расчет общей скидки.
     *
     * @param list Список товаров и их количества.
     * @return Общая скидка.
     */
    private BigDecimal calculateTotalDiscount(List<ProductData> list) {
        return list.stream()
                .map(ProductData::getDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Расчет скидки по оптовому товару.
     *
     * @param product Товар.
     * @param count   Количество.
     * @return Скидка.
     */
    private BigDecimal calculateDiscountByWholesaleProduct(Product product, int count) {
        return Stream.of(product)
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(count)))
                .map(totalSum -> totalSum.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(DISCOUNT_SALE_PERCENT)))
                .map(totalSum -> totalSum.setScale(SCALE, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .abs();
    }

    /**
     * Расчет скидки по неоптовым товарам.
     *
     * @param product      Товар.
     * @param count        Количество.
     * @param discountCard Дисконтная карта.
     * @return Скидка.
     */
    private BigDecimal calculateDiscountByProductWithoutWholesale(Product product, int count, DiscountCard discountCard) {
        return Stream.of(product)
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(count)))
                .map(totalSum -> totalSum.multiply(new BigDecimal(discountCard.getAmount())).divide(BigDecimal.valueOf(100)))
                .map(totalSum -> totalSum.setScale(SCALE, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .abs();
    }

    /**
     * Извлечение списка товаров и их количества из входной строки.
     *
     * @param input Входная строка.
     * @return Список товаров и их количества.
     */
    private List<ProductData> extractProductCount(String input, String pathToFile) {
        List<ProductData> productCountList = new ArrayList<>();
        Pattern pattern = Pattern.compile(PRODUCT_REGEX);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String match = matcher.group();

            // Разделение совпадений на идентификатор продукта и количество
            String[] parts = match.split("-");
            Long productId = Long.parseLong(parts[0]);
            int count = Integer.parseInt(parts[1]);

            var product = productService.findById(productId, pathToFile);

            // Расчет общей суммы по товару
            var totalSum = product.getPrice().multiply(new BigDecimal(count));

            // Расчет скидки по товару
            var discount = BigDecimal.ZERO;
            if (product.getWholesaleProduct() && count >= COUNT_WHOLESALE_PRODUCT) {
                discount = calculateDiscountByWholesaleProduct(product, count);
            } else {
                var discountCard = getDiscountCard(input);
                if (discountCard.isPresent()) {
                    discount = calculateDiscountByProductWithoutWholesale(product, count, discountCard.get());
                }
            }
            productCountList.add(new ProductData(count, product, discount, totalSum));
        }
        return productCountList;
    }

    /**
     * Преобразование массива строк в строку.
     *
     * @param args Массив строк.
     * @return Строка.
     */
    private String arrayToString(String[] args) {
        try {
            StringBuilder sb = new StringBuilder();
            for (String s : args) {
                sb.append(s).append(" ");
            }
            String str = sb.toString();
            str = str.substring(0, str.length() - 1);
            return str;
        } catch (Exception e) {
            writer.writeError(new RuntimeException(INTERNAL_SERVER_ERROR));
            throw new RuntimeException(INTERNAL_SERVER_ERROR);
        }
    }
}