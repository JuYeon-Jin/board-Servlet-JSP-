package com.study.connection.mapper;

import com.study.connection.dto.*;
import com.study.connection.dto.file.FileDownloadDTO;
import com.study.connection.dto.file.FileListDTO;
import com.study.connection.dto.file.FileUploadDTO;
import com.study.connection.dto.post.PostDetailDTO;
import com.study.connection.dto.post.PostInsertDTO;
import com.study.connection.dto.post.PostListDTO;
import com.study.connection.dto.post.PostUpdateDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    // 전체 게시물 목록 조회
    List<PostListDTO> selectAllPosts();

    // 전체 카테고리 목록 조회
    List<CategoryDTO> selectAllCategories();

    // 게시물 상세 조회
    PostDetailDTO selectPostById(Integer postId);

    // 게시물에 해당하는 파일정보(번호, 파일명) 조회
    List<FileListDTO> selectFileByPostId(Integer postId);

    // 조회수 증가
    void updateViewCount(Integer postId);

    // 파일 번호에 해당하는 파일 데이터 조회 (for Download)
    FileDownloadDTO selectFileById(Integer fileId);

    // 게시물 저장
    void addNewPost(PostInsertDTO dto);

    // 게시물에 포함되는 파일 저장
    void addFiles(FileUploadDTO dto);

    // 비밀번호 검증
    boolean matchingPassword();

    // 게시물 수정
    void updatePost(PostUpdateDTO dto);

    // 첨부파일 삭제
    void deleteFile(Integer fileId);

    // 게시물 삭제
    void deletePost(Integer id);
}
