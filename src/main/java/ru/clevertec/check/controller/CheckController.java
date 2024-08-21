package ru.clevertec.check.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.check.model.dto.create.CheckCreateDto;
import ru.clevertec.check.repository.DiscountCardRepository;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.repository.impl.DiscountCardRepositoryImpl;
import ru.clevertec.check.repository.impl.ProductRepositoryImpl;
import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.service.impl.CheckServiceImpl;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Контроллер для работы с чеками.
 * Обрабатывает POST-запросы для создания чеков.
 * Возвращает сгенерированный чек в формате CSV.
 */
@WebServlet
public class CheckController extends HttpServlet {

    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final DiscountCardRepository discountCardRepository = new DiscountCardRepositoryImpl();
    private final CheckService checkService = new CheckServiceImpl(productRepository, discountCardRepository);

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Создает чек на основе переданных данных в теле запроса.
     *
     * @param request  объект запроса
     * @param response объект ответа
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            StringBuffer jb = new StringBuffer();
            String line = null;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            objectMapper.registerModule(new JavaTimeModule());
            CheckCreateDto accountCreateDto = objectMapper.readValue(jb.toString(), CheckCreateDto.class);
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"file.csv\"");
            var check = checkService.getCheck(accountCreateDto);
            Files.copy(Paths.get(check.getPath()), response.getOutputStream());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}