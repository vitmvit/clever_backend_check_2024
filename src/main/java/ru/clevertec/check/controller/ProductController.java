package ru.clevertec.check.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.check.model.dto.create.ProductCreateDto;
import ru.clevertec.check.model.dto.update.ProductUpdateDto;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.service.impl.ProductServiceImpl;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Контроллер для работы с товарами.
 * Обрабатывает GET, POST, PUT, DELETE-запросы для товаров.
 * Возвращает данные в формате JSON.
 */
@WebServlet
public class ProductController extends HttpServlet {

    private final ProductService productService = new ProductServiceImpl();

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Возвращает товар по его идентификатору.
     *
     * @param request  объект запроса
     * @param response объект ответа
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            var productDto = productService.findById(Long.valueOf(id));
            String json = objectMapper.writeValueAsString(productDto);
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
     * Создает новый товар.
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
            var productCreateDto = objectMapper.readValue(jb.toString(), ProductCreateDto.class);
            var productDto = productService.create(productCreateDto);
            String json = objectMapper.writeValueAsString(productDto);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            out.print(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Обновляет существующий товар.
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
            var productUpdateDto = objectMapper.readValue(jb.toString(), ProductUpdateDto.class);
            var productDto = productService.update(Long.parseLong(id), productUpdateDto);
            String json = objectMapper.writeValueAsString(productDto);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            out.print(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Удаляет существующий товар.
     *
     * @param request  объект запроса
     * @param response объект ответа
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            productService.delete(Long.valueOf(id));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
