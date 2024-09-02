package ru.clevertec.check.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.check.model.dto.create.DiscountCardCreateDto;
import ru.clevertec.check.model.dto.update.DiscountCardUpdateDto;
import ru.clevertec.check.service.DiscountCardService;
import ru.clevertec.check.service.impl.DiscountCardServiceImpl;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Контроллер для работы с дисконтными картами.
 * Обрабатывает GET, POST, PUT, DELETE-запросы для карт.
 * Возвращает данные в формате JSON.
 */
@WebServlet
public class DiscountCardController extends HttpServlet {

    private final DiscountCardService discountCardService = new DiscountCardServiceImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Возвращает дисконтную карту по ее идентификатору.
     *
     * @param request  объект запроса
     * @param response объект ответа
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            var discountCardDto = discountCardService.findById(Long.valueOf(id));
            String json = objectMapper.writeValueAsString(discountCardDto);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Создает новую дисконтную карту.
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
            var discountCardCreateDto = objectMapper.readValue(jb.toString(), DiscountCardCreateDto.class);
            var discountCardDto = discountCardService.create(discountCardCreateDto);
            String json = objectMapper.writeValueAsString(discountCardDto);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            out.print(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Обновляет существующую дисконтную карту.
     *
     * @param request  объект запроса
     * @param response объект ответа
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            StringBuffer jb = new StringBuffer();
            String line = null;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            var discountCardUpdateDto = objectMapper.readValue(jb.toString(), DiscountCardUpdateDto.class);
            var discountCardDto = discountCardService.update(Long.parseLong(id), discountCardUpdateDto);
            String json = objectMapper.writeValueAsString(discountCardDto);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            out.print(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Удаляет существующую дисконтную карту.
     *
     * @param request  объект запроса
     * @param response объект ответа
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            discountCardService.delete(Long.valueOf(id));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
