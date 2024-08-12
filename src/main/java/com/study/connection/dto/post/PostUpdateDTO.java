package com.study.connection.dto.post;

import com.study.connection.dto.file.FileUploadDTO;

import java.util.ArrayList;
import java.util.List;

public class PostUpdateDTO {
    private int id;
    private int categoryId;
    private String writer;
    private String password;
    private String hashPassword;
    private String title;
    private String content;
    private List<Integer> deleteFiles;
    private List<FileUploadDTO> files;

    public PostUpdateDTO() {
    }

    public PostUpdateDTO(int id, int categoryId, String writer, String password, String title, String content) {
        this.id = id;
        this.categoryId = categoryId;
        this.writer = writer;
        this.password = password;
        this.title = title;
        this.content = content;
        this.deleteFiles = new ArrayList<>();
        this.files = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public void setHashPassword(String hashPassword) { this.hashPassword = hashPassword; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public void addDeleteFiles(int fileId) {
        this.deleteFiles.add(fileId);
    }

    public List<Integer> getDeleteFiles() {
        return deleteFiles;
    }

    public void addFile(FileUploadDTO dto) {
        this.files.add(dto);
    }

    public List<FileUploadDTO> getFiles() {
        return files;
    }
}
