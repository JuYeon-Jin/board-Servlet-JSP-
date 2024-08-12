package com.study.connection.dto.validation;

public class InsertValidationDTO {
    private int categoryId;
    private String writer;
    private String password;
    private String passwordConfirm;
    private String title;
    private String content;

    public InsertValidationDTO() {
    }

    public InsertValidationDTO(int categoryId, String writer, String password, String passwordConfirm, String title, String content) {
        this.categoryId = categoryId;
        this.writer = writer;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.title = title;
        this.content = content;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
