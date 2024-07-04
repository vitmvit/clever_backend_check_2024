//package ru.clevertec.check.util;
//
//import lombok.Builder;
//import ru.clevertec.check.model.entity.Check;
//import ru.clevertec.check.model.entity.DiscountCard;
//import ru.clevertec.check.model.entity.Product;
//import ru.clevertec.check.model.entity.ProductData;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Builder(setterPrefix = "with")
//public class CheckTestBuilder {
//
//    @Builder.Default
//    private LocalDateTime date = LocalDateTime.of(2024, 1, 3, 9, 12, 15, 156);
//
//    @Builder.Default
//    private List<ProductData> productDataList = List.of(
//            new ProductData(7, new ProductDto(1L, "Milk", new BigDecimal("1.07"), 10, true), new BigDecimal("0.75"), new BigDecimal("7.49")),
//            new ProductData(9, new Product(9L, "Packed bananas 1kg", new BigDecimal("1.10"), 25, true), new BigDecimal("0.99"), new BigDecimal("9.90")),
//            new ProductData(10, new Product(12L, "Packed chicken breasts 1kg", new BigDecimal("10.75"), 18, false), new BigDecimal("4.30"), new BigDecimal("107.50")));
//
//    @Builder.Default
//    private DiscountCard discountCard = new DiscountCard(3L, 3333, (short) 4);
//
//    @Builder.Default
//    private BigDecimal totalSum = new BigDecimal("124.89");
//
//    @Builder.Default
//    private BigDecimal totalDiscount = new BigDecimal("118.85");
//
//    @Builder.Default
//    private BigDecimal totalSumWithDiscount = new BigDecimal("1.07");
//
//    public Check buildCheck() {
//        return new Check(date, productDataList, discountCard, totalSum, totalDiscount, totalSumWithDiscount);
//    }
//}
