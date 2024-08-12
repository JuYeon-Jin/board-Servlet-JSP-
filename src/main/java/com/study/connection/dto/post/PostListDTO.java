package com.study.connection.dto.post;

public class PostListDTO {
    private int id;
    private String title;
    private String createdAt;
    private String updatedAt;
    private String writer;
    private int views;
    private String categoryName;
    private boolean fileExist;

    public PostListDTO() {
    }

    public PostListDTO(int id, String title, String createdAt, String updatedAt, String writer, int views, String categoryName) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.writer = writer;
        this.views = views;
        this.categoryName = categoryName;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }

    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public boolean isFileExist() {
        return fileExist;
    }

    public void setFileExist(boolean fileExist) {
        this.fileExist = fileExist;
    }
}
