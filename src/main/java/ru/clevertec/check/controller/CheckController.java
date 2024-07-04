package ru.clevertec.check.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.clevertec.check.model.dto.create.CheckCreateDto;
import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.service.impl.CheckServiceImpl;

import java.io.BufferedReader;
import java.io.PrintWriter;

@WebServlet
public class CheckController extends HttpServlet {

    private final CheckService checkService = new CheckServiceImpl();

    private final ObjectMapper objectMapper = new ObjectMapper();

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
            var check = checkService.getCheck(accountCreateDto);
            String json = objectMapper.writeValueAsString(check);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            out.print(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
