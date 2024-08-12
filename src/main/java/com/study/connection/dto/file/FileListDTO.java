package com.study.connection.dto.file;

public class FileListDTO {
    int id;
    String fileName;

    public FileListDTO() {
    }

    public FileListDTO(int id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
