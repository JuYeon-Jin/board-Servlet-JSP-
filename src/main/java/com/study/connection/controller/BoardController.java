package com.study.connection.controller;

import com.study.connection.dto.file.FileUploadDTO;
import com.study.connection.dto.post.PostDetailDTO;
import com.study.connection.dto.post.PostInsertDTO;
import com.study.connection.dto.post.PostListDTO;
import com.study.connection.dto.post.PostUpdateDTO;
import com.study.connection.service.BoardService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


@MultipartConfig (
    fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
    maxFileSize = 1024 * 1024 * 10,      // 10 MB
    maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class BoardController extends HttpServlet {

    private final BoardService boardService = new BoardService();

    // HttpServlet.java 의 doGet 과 doPost 를 Override
    // HTTP GET 요청 처리, 쿼리 문자열을 통해 서버에 데이터를 전달
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "private, max-age=0, must-revalidate");
        routeRequest(request, response);
    }

    // HTTP POST 요청 처리, 요청 본문(body)을 통해 데이터를 전달
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        routeRequest(req, resp);
    }

    // 요청 URL 에 따라 메소드 분기 처리
    // request.getParameter() : URL 쿼리 파라미터(GET), 폼 데이터(POST) 모두 작동
    private void routeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // getRequestURI() 는 서블릿 API 에서 제공하는 메서드로, URL의 경로 부분을 반환
        // 즉, 반환값은 http://example.com/myapp/board1/insertPost?id=123&name=test 일 때,
        //            /myapp/board1/insertPost 부분이다.
        String path = request.getRequestURI();

        switch (path) {
            case "/board/posts":       // 게시물 전체 목록 조회
                AllPostsList(request,response);
                break;
            case "/board/post":        // 게시물 상세 조회
                getPostDetail(request,response);
                break;
            case "/board/insert-form": // 게시물 작성 폼
                createPostForm(request,response);
                break;
            case "/board/insert":      // 게시물 작성
                createPost(request,response);
                break;
            case "/board/update-form": // 게시물 수정 폼
                updatePostForm(request,response);
                break;
            case "/board/update":      // 게시물 수정
                updatePost(request,response);
                break;
            case "/board/delete":      // 게시물 삭제
                deletePost(request,response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid Request");
                break;
        }
    }

    // 게시물 전체 목록 조회 (게시물 목록. 카테고리 목록. 전체 게시물 수)
    private void AllPostsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<PostListDTO> dto = boardService.allPostList();
        request.setAttribute("posts", dto);
        request.setAttribute("categories", boardService.allCategoryList());
        request.setAttribute("totalPosts", dto.size());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/postList.jsp");
        dispatcher.forward(request, response);
    }

    // 게시물 상세 조회 (파라미터로 게시물번호 데이터 받아야 함)
    private void getPostDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        PostDetailDTO dto = boardService.getPostDetails(id);
        request.setAttribute("post", dto);
        request.setAttribute("files", boardService.getPostFiles(dto.getId()));
        // 댓글도 들고와야 함.
        // request.setAttribute("comment", boardService.allFileList(postId));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/postArticle.jsp");
        dispatcher.forward(request, response);

    }

    // 게시물 작성 폼
    private void createPostForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categories", boardService.allCategoryList());
        request.getRequestDispatcher("/WEB-INF/views/postFormInsert.jsp").forward(request, response);
    }

    // 게시물 작성
    private void createPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PostInsertDTO dto = new PostInsertDTO(
                Integer.parseInt(request.getParameter("categoryId")),
                request.getParameter("writer"),
                request.getParameter("password"),
                request.getParameter("title"),
                request.getParameter("content")
        );

        for (Part part : request.getParts()) {
            if (part.getName().equals("files")) {
                String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

                if (fileName == null || fileName.trim().isEmpty()) {
                    break;
                }

                FileUploadDTO fileDto = new FileUploadDTO(
                        fileName,
                        part.getSize(),
                        part.getContentType(),
                        part.getInputStream().readAllBytes()
                );

                dto.addFile(fileDto);
            }
        }

        boardService.insertPost(dto);
        response.sendRedirect("/board/posts");
    }

    // 게시물 수정 폼
    private void updatePostForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("post", boardService.getPostDetails(postId));
        request.setAttribute("categories", boardService.allCategoryList());
        request.setAttribute("files", boardService.getPostFiles(postId));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/postFormUpdate.jsp");
        dispatcher.forward(request, response);
    }

    // 게시물 수정
    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PostUpdateDTO dto = new PostUpdateDTO(
                Integer.parseInt(request.getParameter("id")),
                Integer.parseInt(request.getParameter("categoryId")),
                request.getParameter("writer"),
                request.getParameter("password"),
                request.getParameter("title"),
                request.getParameter("content"));

        // 삭제 요청 있었던 파일 데이터 삭제
        for (String fileIdStr : request.getParameterValues("deleteFileId")) {
            if (fileIdStr != null && !fileIdStr.trim().isEmpty()) {
                dto.addDeleteFiles(Integer.parseInt(fileIdStr));
            }
        }

        for (Part part : request.getParts()) {
            if (part.getName().equals("files")) {
                String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

                if (fileName == null || fileName.trim().isEmpty()) {
                    break;
                }

                FileUploadDTO fileDto = new FileUploadDTO(
                        fileName,
                        part.getSize(),
                        part.getContentType(),
                        part.getInputStream().readAllBytes()
                );

                dto.addFile(fileDto);
            }
        }
        boardService.updatePost(dto);
        response.sendRedirect("/board/posts");
    }

    // 게시물 삭제
    private void deletePost(HttpServletRequest request, HttpServletResponse response) {

    }



}
