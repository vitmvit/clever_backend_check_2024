package ru.clevertec.check.service;

import ru.clevertec.check.model.dto.create.CheckCreateDto;

import java.io.File;

public interface CheckService {

    File getCheck(CheckCreateDto dto);

}