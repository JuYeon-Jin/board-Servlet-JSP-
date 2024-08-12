package com.study.connection.dto.post;

public class PostDetailDTO {
    private int id;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String writer;
    private int views;
    private String categoryName;

    public PostDetailDTO() {
    }

    public PostDetailDTO(int id, String title, String content, String createdAt, String updatedAt, String writer, int views, String categoryName) {
        this.id = id;
        this.title = title;
        this.content = content;
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

    public String getContent() {
        if (this.content != null) {
            return this.content.replace("\n", "<br/>");
        }
        return null;
    }

    public void setContent(String content) { this.content = content; }

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

}
