package com.study.connection.controller;

import com.study.connection.dto.file.FileDownloadDTO;
import com.study.connection.service.BoardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@WebServlet("/file/download")
public class FileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 파일 정보 db 에서 조회
        int id = Integer.parseInt(request.getParameter("seq"));
        BoardService boardService = new BoardService();
        FileDownloadDTO dto = boardService.getFileForDownload(id);

        // 파일 이름을 URL 인코딩
        String encodedFileName = URLEncoder.encode(dto.getFileName(), StandardCharsets.UTF_8.toString());

        // 파일을 다운로드할 수 있도록 응답 설정
        response.setContentType(dto.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
        response.setContentLength((int) dto.getFileSize());

        // 바이너리 파일(data) FileOutputStream
        try (OutputStream outStream = response.getOutputStream()) {
            outStream.write(dto.getData());
            outStream.flush();
        }
    }
}
