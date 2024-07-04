package ru.clevertec.check.converter;

import org.mapstruct.*;
import ru.clevertec.check.model.dto.ProductDto;
import ru.clevertec.check.model.dto.create.ProductCreateDto;
import ru.clevertec.check.model.dto.update.ProductUpdateDto;
import ru.clevertec.check.model.entity.Product;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductConverter {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "quantity", source = "quantityInStock"),
            @Mapping(target = "isWholesale", source = "wholesaleProduct")
    })
    ProductDto convert(Product source);

    @Mappings({
            @Mapping(target = "quantityInStock", source = "quantity"),
            @Mapping(target = "wholesaleProduct", source = "isWholesale")
    })
    Product convert(ProductCreateDto source);

    @Mappings({
            @Mapping(target = "quantityInStock", source = "quantity"),
            @Mapping(target = "wholesaleProduct", source = "isWholesale")
    })
    Product merge(@MappingTarget Product source, ProductUpdateDto dto);
}
