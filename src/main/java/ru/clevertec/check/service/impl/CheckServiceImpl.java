package ru.clevertec.check.service.impl;

import ru.clevertec.check.exception.GenerateCheckException;
import ru.clevertec.check.exception.NotFoundException;
import ru.clevertec.check.model.dto.create.CheckCreateDto;
import ru.clevertec.check.model.entity.*;
import ru.clevertec.check.repository.DiscountCardRepository;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.check.repository.impl.ProductRepositoryImpl;
import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.writer.Writer;
import ru.clevertec.check.writer.impl.WriterImpl;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static ru.clevertec.check.constant.Constant.*;

/**
 * Реализация сервиса вывода чеков в консоль.
 */
public class CheckServiceImpl implements CheckService {

    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final DiscountCardRepository discountCardRepository = new DiscountCardRepositoryImpl();
    private final Writer writer = new WriterImpl();

    /**
     * Генерирует чек.
     *
     * @param dto данные для создания чека
     * @return файл с чеком
     * @throws GenerateCheckException если произошла ошибка при генерации чека
     */
    @Override
    public File getCheck(CheckCreateDto dto) {
        try {
            // Объединение дубликатов товаров в списке
            dto.setProducts(combineDuplicate(dto.getProducts()));

            List<ProductData> productDataList = new ArrayList<>();
            Optional<DiscountCard> discountCard = Optional.empty();

            // Перебор товаров в чеке
            for (var item : dto.getProducts()) {
                // Поиск товара по его идентификатору
                var product = productRepository.findById(item.getId()).orElseThrow(NotFoundException::new);
                // Расчет скидки для товара
                var discount = BigDecimal.ZERO;
                if (product.getWholesaleProduct() && item.getQuantity() >= COUNT_WHOLESALE_PRODUCT) {
                    // Расчет скидки для товара, продаваемого оптом
                    discount = calculateDiscountByWholesaleProduct(product, item.getQuantity());
                } else {
                    // Расчет скидки для товара с учетом дисконтной карты
                    if (dto.getDiscountCard() != null) {
                        discountCard = discountCardRepository.findByNumber(dto.getDiscountCard());
                        discount = calculateDiscountByProductWithoutWholesale(product, item.getQuantity(), discountCard.get());
                    }
                }

                // Расчет общей суммы для товара
                var totalSum = product.getPrice().multiply(new BigDecimal(item.getQuantity()));
                productDataList.add(new ProductData(item.getQuantity(), product, discount, totalSum));
            }

            // Расчет общей суммы покупки
            var totalSum = calculateTotalSum(productDataList);
            // Расчет общей скидки по покупке
            var totalDiscount = calculateTotalDiscount(productDataList);
            // Расчет общей суммы покупки с учетом скидки
            var totalSumWithDiscount = totalSum.subtract(totalDiscount);
            // Проверка достаточности средств на дебетовой карте
            if (dto.getBalanceDebitCard().compareTo(totalSumWithDiscount) > 0) {
                // Обновление количества товаров в наличии
                updateProductCount(productDataList);
                // Запись чека
                return writer.writeCheck(new Check(LocalDateTime.now(), productDataList, discountCard.orElse(null), totalSum, totalDiscount, totalSumWithDiscount));
            } else {
                // Выброс исключения в случае недостатка средств
                throw new GenerateCheckException(NOT_ENOUGH_MONEY);
            }
        } catch (Exception e) {
            // Выброс исключения в случае ошибки при генерации чека
            throw new GenerateCheckException(BAD_REQUEST);
        }
    }

    /**
     * Обновляет количество товаров в наличии после покупки.
     *
     * @param productDataList список данных о товарах в чеке
     * @throws GenerateCheckException если количество товара в наличии меньше, чем количество товара в чеке
     */
    private void updateProductCount(List<ProductData> productDataList) {
        for (var item : productDataList) {
            var product = productRepository.findById(item.getProduct().getId()).orElseThrow(NotFoundException::new);
            if (item.getCount() < product.getQuantityInStock()) {
                product.setQuantityInStock(product.getQuantityInStock() - item.getCount());
                productRepository.update(product);
            } else if (item.getCount() == product.getQuantityInStock()) {
                productRepository.delete(product.getId());
            } else {
                throw new GenerateCheckException(BAD_REQUEST);
            }
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
     * Объединяет дубликаты товаров в списке.
     *
     * @param productList список товаров
     * @return список товаров без дублей
     */
    private List<CheckProduct> combineDuplicate(List<CheckProduct> productList) {
        Map<Integer, Integer> productQuantities = new HashMap<>();
        for (int i = 0; i < productList.size(); i++) {
            int id = Integer.parseInt(productList.get(i).getId().toString());
            int quantity = Integer.parseInt(productList.get(i).getQuantity().toString());

            if (productQuantities.containsKey(id)) {
                productQuantities.put(id, productQuantities.get(id) + quantity);
            } else {
                productQuantities.put(id, quantity);
            }
        }
        List<CheckProduct> newProductList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
            newProductList.add(new CheckProduct(Long.valueOf(entry.getKey()), entry.getValue()));
        }
        return newProductList;
    }
}
