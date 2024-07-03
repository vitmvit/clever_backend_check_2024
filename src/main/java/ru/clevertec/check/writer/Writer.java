package ru.clevertec.check.writer;

import ru.clevertec.check.model.Check;

public interface Writer {

    void writeCheck(Check check, String saveToFile);

    void writeError(Exception e, String saveToFile);

    void writeError(Exception e);
}