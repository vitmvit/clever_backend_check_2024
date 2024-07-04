//package ru.clevertec.check.parser.impl;
//
//import ru.clevertec.check.exception.ParseException;
//import ru.clevertec.check.model.dto.DiscountCardDto;
//import ru.clevertec.check.model.dto.ProductDto;
//import ru.clevertec.check.model.entity.Check;
//import ru.clevertec.check.model.entity.ProductData;
//import ru.clevertec.check.parser.ArgsParser;
//import ru.clevertec.check.service.CheckService;
//import ru.clevertec.check.service.DiscountCardService;
//import ru.clevertec.check.service.ProductService;
//import ru.clevertec.check.service.impl.CheckServiceImpl;
//import ru.clevertec.check.service.impl.DiscountCardServiceImpl;
//import ru.clevertec.check.service.impl.ProductServiceImpl;
//import ru.clevertec.check.util.RegexValidator;
//import ru.clevertec.check.writer.Writer;
//import ru.clevertec.check.writer.impl.WriterImpl;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Stream;
//
//import static ru.clevertec.check.constant.Constant.*;
//
///**
// * Реализация парсера аргументов.
// */
//public class ArgsParserImpl implements ArgsParser {
//
//    private final ProductService productService = new ProductServiceImpl();
//    private final DiscountCardService discountCardService = new DiscountCardServiceImpl();
//    private final Writer writer = new WriterImpl();
//    private final CheckService checkService = new CheckServiceImpl();
//
//    /**
//     * Создание чека.
//     *
//     * @param args Аргументы командной строки.
//     * @return Сгенерированный чек.
//     */
//    public Check getCheck(String[] args) {
////        if (args.length == 0) {
////            writer.writeError(new ParseException(BAD_REQUEST));
////            throw new ParseException(BAD_REQUEST);
////        }
////        var inputString = arrayToString(args);
//        // Проверка валидности входной строки
//        if (RegexValidator.isValid(inputString, VALIDATION_REGEX)) {
//            inputString = combineDuplicate(inputString);
//            var saveToFile = getSaveToFile(inputString);
//            if (saveToFile == null) {
//                writer.writeError(new ParseException(BAD_REQUEST));
//                throw new ParseException(BAD_REQUEST);
//            }
//
//            var productCountList = extractProductCount(inputString);
//
//            // Рассчитать общую сумму
//            var totalSum = calculateTotalSum(productCountList);
//
//            // Рассчитать общую скидку
//            var totalDiscount = calculateTotalDiscount(productCountList);
//
//            // Рассчитать общую сумму с учетом скидки
//            var totalSumWithDiscount = totalSum.subtract(totalDiscount);
//
//            // Получить дисконтную карту, если она есть
//            var discountCard = getDiscountCard(inputString).orElse(null);
//            var balance = getBalance(inputString);
//            if (balance.compareTo(totalSumWithDiscount) > 0) {
//                var check = new Check(LocalDateTime.now(), productCountList, discountCard, totalSum, totalDiscount, totalSumWithDiscount);
//                writer.writeCheck(check, saveToFile);
//                checkService.showCheck(check);
//                return check;
//            } else {
//                writer.writeError(new ParseException(NOT_ENOUGH_MONEY));
//                throw new ParseException(NOT_ENOUGH_MONEY);
//            }
//        } else {
//            writer.writeError(new ParseException(BAD_REQUEST));
//            throw new ParseException(BAD_REQUEST);
//        }
//    }
//
//    /**
//     * Объединяет дублирующиеся записи о продукте в строке, сохраняя итоговое количество каждого продукта.
//     * Пример:
//     * combineDuplicate("1-2 2-3 1-5") == "1-7 2-3"
//     *
//     * @param input строка, содержащая список пар id-количество через пробелы
//     * @return обновленную строку со скомбинированными дубликатами
//     */
//    private String combineDuplicate(String input) {
//        String products = getProductsString(input);
//        String[] pairs = products.split(" ");
//
//        Map<Integer, Integer> productQuantities = new HashMap<>();
//        for (String pair : pairs) {
//            String[] parts = pair.split("-");
//            int id = Integer.parseInt(parts[0]);
//            int quantity = Integer.parseInt(parts[1]);
//
//            // Если id товара уже существует в карте, суммируем количество товара
//            if (productQuantities.containsKey(id)) {
//                productQuantities.put(id, productQuantities.get(id) + quantity);
//            } else {
//                // В противном случае добавляем новую связку в карту
//                productQuantities.put(id, quantity);
//            }
//        }
//
//        StringBuilder resultProductsString = new StringBuilder();
//        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
//            resultProductsString.append(entry.getKey()).append("-").append(entry.getValue()).append(" ");
//        }
//        resultProductsString.deleteCharAt(resultProductsString.length() - 1);
//        return input.replaceAll(PRODUCTS_REGEX, resultProductsString.toString());
//    }
//
//    /**
//     * Получение дисконтной карты.
//     *
//     * @param input Входная строка.
//     * @return Дисконтная карта.
//     */
//    private Optional<DiscountCardDto> getDiscountCard(String input) {
//        Pattern pattern = Pattern.compile(DISCOUNT_CARD_REGEX);
//        Matcher matcher = pattern.matcher(input);
//
//        if (matcher.find()) {
//            return Optional.of(discountCardService.findByNumber(Integer.parseInt(matcher.group(1))));
//        } else {
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * Получение баланса.
//     *
//     * @param input Входная строка.
//     * @return Баланс.
//     */
//    private BigDecimal getBalance(String input) {
//        Pattern pattern = Pattern.compile(BALANCE_REGEX);
//        Matcher matcher = pattern.matcher(input);
//        if (matcher.find()) {
//            return new BigDecimal(matcher.group(1));
//        } else {
//            writer.writeError(new ParseException(INTERNAL_SERVER_ERROR));
//            throw new ParseException(INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Получение строки списка товаров.
//     *
//     * @param input Входная строка.
//     * @return Баланс.
//     */
//    private String getProductsString(String input) {
//        Pattern pattern = Pattern.compile(PRODUCTS_REGEX);
//        Matcher matcher = pattern.matcher(input);
//        if (matcher.find()) {
//            return matcher.group(1);
//        } else {
//            writer.writeError(new ParseException(INTERNAL_SERVER_ERROR));
//            throw new ParseException(INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * Получение пути к файлу, куда записывать данные.
//     *
//     * @param input Входная строка.
//     * @return Баланс.
//     */
//    private String getSaveToFile(String input) {
//        Pattern pattern = Pattern.compile(SAVE_TO_FILE_REGEX);
//        Matcher matcher = pattern.matcher(input);
//        if (matcher.find()) {
//            return matcher.group(1);
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * Получение url бд, где лежат данные.
//     *
//     * @param input Входная строка.
//     * @return Баланс.
//     */
//    private String getDatasourceUrl(String input) {
//        Pattern pattern = Pattern.compile(DATASOURCE_URL_REGEX);
//        Matcher matcher = pattern.matcher(input);
//        if (matcher.find()) {
//            return matcher.group(1);
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * Получение username.
//     *
//     * @param input Входная строка.
//     * @return Баланс.
//     */
//    private String getDatasourceUsername(String input) {
//        Pattern pattern = Pattern.compile(DATASOURCE_USERNAME_REGEX);
//        Matcher matcher = pattern.matcher(input);
//        if (matcher.find()) {
//            return matcher.group(1);
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * Получение password.
//     *
//     * @param input Входная строка.
//     * @return Баланс.
//     */
//    private String getDatasourcePassword(String input) {
//        Pattern pattern = Pattern.compile(DATASOURCE_PASSWORD_REGEX);
//        Matcher matcher = pattern.matcher(input);
//        if (matcher.find()) {
//            return matcher.group(1);
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * Расчет общей суммы.
//     *
//     * @param list Список товаров и их количества.
//     * @return Общая сумма.
//     */
//    private BigDecimal calculateTotalSum(List<ProductData> list) {
//        return list.stream()
//                .map(ProductData::getTotalSum)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    /**
//     * Расчет общей скидки.
//     *
//     * @param list Список товаров и их количества.
//     * @return Общая скидка.
//     */
//    private BigDecimal calculateTotalDiscount(List<ProductData> list) {
//        return list.stream()
//                .map(ProductData::getDiscount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    /**
//     * Расчет скидки по оптовому товару.
//     *
//     * @param product Товар.
//     * @param count   Количество.
//     * @return Скидка.
//     */
//    private BigDecimal calculateDiscountByWholesaleProduct(ProductDto product, int count) {
//        return Stream.of(product)
//                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(count)))
//                .map(totalSum -> totalSum.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(DISCOUNT_SALE_PERCENT)))
//                .map(totalSum -> totalSum.setScale(SCALE, RoundingMode.HALF_UP))
//                .reduce(BigDecimal.ZERO, BigDecimal::add)
//                .abs();
//    }
//
//    /**
//     * Расчет скидки по неоптовым товарам.
//     *
//     * @param product      Товар.
//     * @param count        Количество.
//     * @param discountCard Дисконтная карта.
//     * @return Скидка.
//     */
//    private BigDecimal calculateDiscountByProductWithoutWholesale(ProductDto product, int count, DiscountCardDto discountCard) {
//        return Stream.of(product)
//                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(count)))
//                .map(totalSum -> totalSum.multiply(new BigDecimal(discountCard.getDiscountAmount())).divide(BigDecimal.valueOf(100)))
//                .map(totalSum -> totalSum.setScale(SCALE, RoundingMode.HALF_UP))
//                .reduce(BigDecimal.ZERO, BigDecimal::add)
//                .abs();
//    }
//
//    /**
//     * Извлечение списка товаров и их количества из входной строки.
//     *
//     * @param input Входная строка.
//     * @return Список товаров и их количества.
//     */
//    private List<ProductData> extractProductCount(String input) {
//        List<ProductData> productCountList = new ArrayList<>();
//        Pattern pattern = Pattern.compile(PRODUCT_REGEX);
//        Matcher matcher = pattern.matcher(input);
//
//        while (matcher.find()) {
//            String match = matcher.group();
//
//            // Разделение совпадений на идентификатор продукта и количество
//            String[] parts = match.split("-");
//            Long productId = Long.parseLong(parts[0]);
//            int count = Integer.parseInt(parts[1]);
//
//            var product = productService.findById(productId);
//
//            // Расчет общей суммы по товару
//            var totalSum = product.getPrice().multiply(new BigDecimal(count));
//
//            // Расчет скидки по товару
//            var discount = BigDecimal.ZERO;
//            if (product.getIsWholesale() && count >= COUNT_WHOLESALE_PRODUCT) {
//                discount = calculateDiscountByWholesaleProduct(product, count);
//            } else {
//                var discountCard = getDiscountCard(input);
//                if (discountCard.isPresent()) {
//                    discount = calculateDiscountByProductWithoutWholesale(product, count, discountCard.get());
//                }
//            }
//            productCountList.add(new ProductData(count, product, discount, totalSum));
//        }
//        return productCountList;
//    }
//
//    /**
//     * Преобразование массива строк в строку.
//     *
//     * @param args Массив строк.
//     * @return Строка.
//     */
//    private String arrayToString(String[] args) {
//        try {
//            StringBuilder sb = new StringBuilder();
//            for (String s : args) {
//                sb.append(s).append(" ");
//            }
//            String str = sb.toString();
//            str = str.substring(0, str.length() - 1);
//            return str;
//        } catch (Exception e) {
//            writer.writeError(new ParseException(INTERNAL_SERVER_ERROR));
//            throw new ParseException(INTERNAL_SERVER_ERROR);
//        }
//    }
//}