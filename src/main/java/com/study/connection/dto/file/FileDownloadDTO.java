package com.study.connection.dto.file;

public class FileDownloadDTO {
    private String fileName;       // 파일 이름
    private long fileSize;         // 파일 크기
    private String contentType;    // MIME 타입
    private byte[] data;           // 파일 데이터

    public FileDownloadDTO() {
    }

    public FileDownloadDTO(String fileName, long fileSize, String contentType, byte[] data) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
