package ru.clevertec.check.service;

import ru.clevertec.check.model.dto.create.CheckCreateDto;
import ru.clevertec.check.model.entity.Check;

public interface CheckService {

    Check getCheck(CheckCreateDto dto);

//    void showCheck(Check check);
}