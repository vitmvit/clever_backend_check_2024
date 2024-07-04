package ru.clevertec.check.model.dto.create;

import lombok.Getter;
import lombok.Setter;
import ru.clevertec.check.model.entity.CheckProduct;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CheckCreateDto {

    private List<CheckProduct> products;
    private Integer discountCard;
    private BigDecimal balanceDebitCard;
}
