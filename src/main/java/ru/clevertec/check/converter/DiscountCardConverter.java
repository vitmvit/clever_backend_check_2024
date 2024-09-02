package ru.clevertec.check.converter;

import org.mapstruct.*;
import ru.clevertec.check.model.dto.DiscountCardDto;
import ru.clevertec.check.model.dto.create.DiscountCardCreateDto;
import ru.clevertec.check.model.dto.update.DiscountCardUpdateDto;
import ru.clevertec.check.model.entity.DiscountCard;

/**
 * Конвертер для работы с дисконтными картами.
 * Преобразует объекты из одного представления в другое.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiscountCardConverter {

    /**
     * Преобразует объект {@link DiscountCard} в {@link DiscountCardDto}.
     *
     * @param source исходный объект
     * @return преобразованный объект
     */
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "discountCard", source = "number"),
            @Mapping(target = "discountAmount", source = "amount")
    })
    DiscountCardDto convert(DiscountCard source);

    /**
     * Преобразует объект {@link DiscountCardCreateDto} в {@link DiscountCard}.
     *
     * @param source исходный объект
     * @return преобразованный объект
     */
    @Mappings({
            @Mapping(target = "number", source = "discountCard"),
            @Mapping(target = "amount", source = "discountCard")
    })
    DiscountCard convert(DiscountCardCreateDto source);

    /**
     * Сливает данные из {@link DiscountCardUpdateDto} в объект {@link DiscountCard}.
     *
     * @param source объект для слияния
     * @param dto    объект с обновленными данными
     */
    @Mappings({
            @Mapping(target = "number", source = "discountCard"),
            @Mapping(target = "amount", source = "discountAmount")
    })
    DiscountCard merge(@MappingTarget DiscountCard source, DiscountCardUpdateDto dto);
}
