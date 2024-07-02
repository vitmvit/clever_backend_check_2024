package ru.clevertec.check;


import ru.clevertec.check.parser.impl.ArgsParserImpl;
import ru.clevertec.check.service.impl.CheckServiceImpl;
import ru.clevertec.check.writer.impl.WriterImpl;

/**
 * Точка входа в приложение.
 */
public class CheckRunner {

    /**
     * Главная функция приложения.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {

        var parser = new ArgsParserImpl();
        var writer = new WriterImpl();
        var checkService = new CheckServiceImpl();

        writer.writeCheck(parser.getCheck(args));
        checkService.showCheck(parser.getCheck(args));
    }
}