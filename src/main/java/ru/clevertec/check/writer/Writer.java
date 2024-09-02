package ru.clevertec.check.writer;

import ru.clevertec.check.model.entity.Check;

import java.io.File;

public interface Writer {

    File writeCheck(Check check);
}