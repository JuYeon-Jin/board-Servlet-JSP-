package com.study.connection.service;

import com.study.connection.dto.*;
import com.study.connection.dto.file.FileDownloadDTO;
import com.study.connection.dto.file.FileListDTO;
import com.study.connection.dto.file.FileUploadDTO;
import com.study.connection.dto.post.PostDetailDTO;
import com.study.connection.dto.post.PostInsertDTO;
import com.study.connection.dto.post.PostListDTO;
import com.study.connection.dto.post.PostUpdateDTO;
import com.study.connection.mapper.BoardMapper;
import com.study.connection.mapper.MyBatisInitializer;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BoardService {

    private final SqlSession session;
    private final BoardMapper boardMapper;

    public BoardService() {
        // 생성자에서 세션과 매퍼 초기화
        this.session = MyBatisInitializer.getSqlSession();
        this.boardMapper = session.getMapper(BoardMapper.class);
    }

    // 전체 게시물 목록 불러오기
    public List<PostListDTO> allPostList() {
        return boardMapper.selectAllPosts();
    }

    // 전체 카테고리 목록 불러오기
    public List<CategoryDTO> allCategoryList() {
        return boardMapper.selectAllCategories();
    }

    // 게시물 상세 내용 불러오기 (조회수 count++)
    public PostDetailDTO getPostDetails(Integer postId) {
        boardMapper.updateViewCount(postId);
        session.commit();
        return boardMapper.selectPostById(postId);
    }

    // 게시물에 포함되어있는 첨부파일 불러오기
    public List<FileListDTO> getPostFiles(Integer postId) {
        return boardMapper.selectFileByPostId(postId);
    }

    // 파일 ID → 파일 데이터 불러오기
    public FileDownloadDTO getFileForDownload(Integer id) {
        return boardMapper.selectFileById(id);
    }

    // 게시물 생성 (한 게시물 에 파일 여러개 추가를 위한 반복문 포함)
    public void insertPost(PostInsertDTO dto) {
        PasswordHash hash = new PasswordHash();
        dto.setHashPassword(hash.hashPassword(dto.getPassword()));

        boardMapper.addNewPost(dto);
        session.commit();

        if (dto.getFiles().size() > 0) {
            for (FileUploadDTO file : dto.getFiles()) {
                file.setPostId(dto.getId());
                boardMapper.addFiles(file);
                session.commit();
            }
        }
    }

    // 게시물 수정
    public void updatePost(PostUpdateDTO dto) {
        PasswordHash hash = new PasswordHash();
        dto.setHashPassword(hash.hashPassword(dto.getPassword()));

        boardMapper.updatePost(dto);
        session.commit();

        if (dto.getDeleteFiles().size() > 0) {
            for (int fileId : dto.getDeleteFiles()) {
                boardMapper.deleteFile(fileId);
                session.commit();
            }
        }

        if (dto.getFiles().size() > 0) {
            for (FileUploadDTO file : dto.getFiles()) {
                file.setPostId(dto.getId());
                boardMapper.addFiles(file);
                session.commit();
            }
        }
    }

    // 파일 데이터 삭제
    public void deletePost(Integer id) {

    }

}
