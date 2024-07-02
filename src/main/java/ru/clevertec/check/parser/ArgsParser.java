package ru.clevertec.check.parser;

import ru.clevertec.check.model.Check;

public interface ArgsParser {

    Check getCheck(String[] args);
}
