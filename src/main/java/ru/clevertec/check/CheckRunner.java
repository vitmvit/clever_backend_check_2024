package ru.clevertec.check;


import ru.clevertec.check.parser.impl.ArgsParserImpl;

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
        parser.getCheck(args);
    }
}