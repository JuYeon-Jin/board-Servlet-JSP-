package com.study.connection.controller;

import com.study.connection.service.ValidationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ValidationController extends HttpServlet {

    private final ValidationService validationService = new ValidationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println("GET request received at " + path);
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println("POST request received at " + path);

        switch (path) {
            case "/validation/insert": insertValidation(request, response);
                            break;
            case "/validation/update": updateValidation(request, response);
                            break;
            default: response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid Request");
                     break;
        }

    }

    private void insertValidation(HttpServletRequest request, HttpServletResponse response) {
        boolean isValid = validationService.validateInsertData(Integer.parseInt(request.getParameter("categoryId")),
                request.getParameter("writer"),
                request.getParameter("password"),
                request.getParameter("passwordConfirm"),
                request.getParameter("title"),
                request.getParameter("content"));

        if (isValid) {
            response.setStatus(HttpServletResponse.SC_OK);          // 200
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
        }
    }

    private void updateValidation(HttpServletRequest request, HttpServletResponse response) {
        boolean isValid = validationService.validateUpdateData(Integer.parseInt(request.getParameter("categoryId")),
                request.getParameter("writer"),
                request.getParameter("password"),
                request.getParameter("title"),
                request.getParameter("content"));

        if (isValid) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
