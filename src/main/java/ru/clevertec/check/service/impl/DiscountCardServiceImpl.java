package ru.clevertec.check.service.impl;

import ru.clevertec.check.converter.DiscountCardConverter;
import ru.clevertec.check.converter.DiscountCardConverterImpl;
import ru.clevertec.check.exception.NotFoundException;
import ru.clevertec.check.model.dto.DiscountCardDto;
import ru.clevertec.check.model.dto.create.DiscountCardCreateDto;
import ru.clevertec.check.model.dto.update.DiscountCardUpdateDto;
import ru.clevertec.check.repository.DiscountCardRepository;
import ru.clevertec.check.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.check.service.DiscountCardService;

/**
 * Реализация сервиса дисконтных карт.
 */
public class DiscountCardServiceImpl implements DiscountCardService {

    private final DiscountCardRepository discountCardRepository = new DiscountCardRepositoryImpl();
    private final DiscountCardConverter discountCardConverter = new DiscountCardConverterImpl();

    /**
     * Находит дисконтную карту по её идентификатору.
     *
     * @param id Идентификатор дисконтной карты.
     * @return DTO дисконтной карты.
     */
    @Override
    public DiscountCardDto findById(Long id) {
        return discountCardConverter.convert(discountCardRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    /**
     * Создает новую дисконтную карту.
     *
     * @param dto DTO дисконтной карты.
     * @return DTO созданной дисконтной карты.
     */
    @Override
    public DiscountCardDto create(DiscountCardCreateDto dto) {
        return discountCardConverter.convert(discountCardRepository.create(discountCardConverter.convert(dto)));
    }

    /**
     * Обновляет дисконтную карту.
     *
     * @param id  id одновляемой карты.
     * @param dto DTO дисконтной карты.
     * @return DTO обновленной дисконтной карты.
     */
    @Override
    public DiscountCardDto update(Long id, DiscountCardUpdateDto dto) {
        var discountCard = discountCardRepository.findById(id).orElseThrow(NotFoundException::new);
        return discountCardConverter.convert(discountCardConverter.merge(discountCard, dto));
    }

    /**
     * Удаляет дисконтную карту.
     *
     * @param id Идентификатор дисконтной карты.
     */
    @Override
    public void delete(Long id) {
        discountCardRepository.delete(id);
    }
}