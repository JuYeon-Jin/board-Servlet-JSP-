package com.study.connection.service;

import com.study.connection.dto.PostListDTO;
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

    public List<PostListDTO> allPostList() {
        return boardMapper.selectAllPosts();
    }

}
