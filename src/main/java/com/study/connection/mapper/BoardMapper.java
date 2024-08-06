package com.study.connection.mapper;

import com.study.connection.dto.PostListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<PostListDTO> selectAllPosts();
}
