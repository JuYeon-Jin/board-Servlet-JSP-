package com.study.connection.controller;

import com.study.connection.service.BoardService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BoardController extends HttpServlet {

    private final BoardService boardService = new BoardService();

    // HttpServlet.java 의 doGet 과 doPost 를 Override
    // HTTP GET 요청 처리, 쿼리 문자열을 통해 서버에 데이터를 전달
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        routeRequest(request, response);
    }

    // HTTP POST 요청 처리, 요청 본문(body)을 통해 데이터를 전달
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        routeRequest(req, resp);
    }

    // 요청 URL 에 따라 메소드 분기 처리
    private void routeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // getRequestURI() 는 서블릿 API 에서 제공하는 메서드로, URL의 경로 부분을 반환
        // 즉, 반환값은 http://example.com/myapp/board1/insertPost?id=123&name=test 일 때,
        //            /myapp/board1/insertPost 부분이다.
        String path = request.getRequestURI();
        String endPoint = "";

        switch (path) {
            case "/board1/posts":       // 게시물 전체 목록 조회
                getPostsList(request,response);
                break;
            case "/board1/insert":      // 게시물 작성
                createPost(request,response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid Request");
                break;
        }

    }

    // 게시물 전체 목록 조회
    private void getPostsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("posts", boardService.allPostList());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/postList.jsp");
        dispatcher.forward(request, response);

    }

    // 게시물 작성
    private void createPost(HttpServletRequest request, HttpServletResponse response) {
        // request.getParameter() : URL 쿼리 파라미터(GET), 폼 데이터(POST) 모두 작동
        // String title = request.getParameter("title");
    }



}
