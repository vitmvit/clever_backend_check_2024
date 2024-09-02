package ru.clevertec.check.model.entity;

import lombok.*;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class CheckProduct {

    private Long id;
    private Integer quantity;
}
