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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static ru.clevertec.check.constant.Constant.*;

/**
 * Реализация сервиса вывода чеков в консоль.
 */
public class CheckServiceImpl implements CheckService {

    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final DiscountCardRepository discountCardRepository = new DiscountCardRepositoryImpl();
    private final Writer writer = new WriterImpl();

    @Override
    public Check getCheck(CheckCreateDto dto) {
        try {
            dto.setProducts(combineDuplicate(dto.getProducts()));
            List<ProductData> productDataList = new ArrayList<>();
            var discountCard = discountCardRepository.findByNumber(dto.getDiscountCard());
            for (var item : dto.getProducts()) {
                var product = productRepository.findById(item.getId()).orElseThrow(NotFoundException::new);
                var discount = BigDecimal.ZERO;
                if (product.getWholesaleProduct() && item.getQuantity() >= COUNT_WHOLESALE_PRODUCT) {
                    discount = calculateDiscountByWholesaleProduct(product, item.getQuantity());
                } else {
                    if (discountCard.isPresent()) {
                        discount = calculateDiscountByProductWithoutWholesale(product, item.getQuantity(), discountCard.get());
                    }
                }
                var totalSum = product.getPrice().multiply(new BigDecimal(item.getQuantity()));
                productDataList.add(new ProductData(item.getQuantity(), product, discount, totalSum));
            }
            var totalSum = calculateTotalSum(productDataList);
            var totalDiscount = calculateTotalDiscount(productDataList);
            var totalSumWithDiscount = totalSum.subtract(totalDiscount);
            if (dto.getBalanceDebitCard().compareTo(totalSumWithDiscount) > 0) {
                var check = new Check(LocalDateTime.now(), productDataList, discountCard.get(), totalSum, totalDiscount, totalSumWithDiscount);
//                CsvMapper mapper = new CsvMapper();
//                CsvSchema schema = mapper.schemaFor(Check.class)
//                        .withColumnSeparator(';')
//                        .withoutQuoteChar()
//                        .withHeader();
//                ObjectWriter writer = mapper.writer(schema);
//                writer.writeValue(new FileWriter("test.csv", StandardCharsets.UTF_8), check);
                writer.writeCheck(new Check(LocalDateTime.now(), productDataList, discountCard.get(), totalSum, totalDiscount, totalSumWithDiscount));
                return check;
            } else {
                throw new GenerateCheckException(NOT_ENOUGH_MONEY);
            }
        } catch (Exception e) {
            throw new GenerateCheckException(BAD_REQUEST);
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
