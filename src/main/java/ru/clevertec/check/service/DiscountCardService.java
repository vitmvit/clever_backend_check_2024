package ru.clevertec.check.service;

import ru.clevertec.check.model.dto.DiscountCardDto;
import ru.clevertec.check.model.dto.create.DiscountCardCreateDto;
import ru.clevertec.check.model.dto.update.DiscountCardUpdateDto;

public interface DiscountCardService {

    DiscountCardDto findById(Long id);

    DiscountCardDto create(DiscountCardCreateDto dto);

    DiscountCardDto update(Long id, DiscountCardUpdateDto dto);

    void delete(Long id);
}
