package ru.clevertec.check.converter;

import org.mapstruct.*;
import ru.clevertec.check.model.dto.ProductDto;
import ru.clevertec.check.model.dto.create.ProductCreateDto;
import ru.clevertec.check.model.dto.update.ProductUpdateDto;
import ru.clevertec.check.model.entity.Product;

/**
 * Конвертер для работы с товарами.
 * Преобразует объекты из одного представления в другое.
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductConverter {

    /**
     * Преобразует объект {@link Product} в {@link ProductDto}.
     *
     * @param source исходный объект
     * @return преобразованный объект
     */
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "quantity", source = "quantityInStock"),
            @Mapping(target = "isWholesale", source = "wholesaleProduct")
    })
    ProductDto convert(Product source);

    /**
     * Преобразует объект {@link ProductCreateDto} в {@link Product}.
     *
     * @param source исходный объект
     * @return преобразованный объект
     */
    @Mappings({
            @Mapping(target = "quantityInStock", source = "quantity"),
            @Mapping(target = "wholesaleProduct", source = "isWholesale")
    })
    Product convert(ProductCreateDto source);

    /**
     * Сливает данные из {@link ProductUpdateDto} в объект {@link Product}.
     *
     * @param source объект для слияния
     * @param dto    объект с обновленными данными
     */
    @Mappings({
            @Mapping(target = "quantityInStock", source = "quantity"),
            @Mapping(target = "wholesaleProduct", source = "isWholesale")
    })
    Product merge(@MappingTarget Product source, ProductUpdateDto dto);
}
