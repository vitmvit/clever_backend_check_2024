package ru.clevertec.check.converter;

import org.mapstruct.*;
import ru.clevertec.check.model.dto.DiscountCardDto;
import ru.clevertec.check.model.dto.create.DiscountCardCreateDto;
import ru.clevertec.check.model.dto.update.DiscountCardUpdateDto;
import ru.clevertec.check.model.entity.DiscountCard;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiscountCardConverter {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "discountCard", source = "number"),
            @Mapping(target = "discountAmount", source = "amount")
    })
    DiscountCardDto convert(DiscountCard source);

    @Mappings({
            @Mapping(target = "number", source = "discountCard"),
            @Mapping(target = "amount", source = "discountCard")
    })
    DiscountCard convert(DiscountCardCreateDto source);

    @Mappings({
            @Mapping(target = "number", source = "discountCard"),
            @Mapping(target = "amount", source = "discountAmount")
    })
    DiscountCard merge(@MappingTarget DiscountCard source, DiscountCardUpdateDto dto);
}
