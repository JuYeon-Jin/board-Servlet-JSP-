package com.study.connection.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ValidationMapper {

    // 카테고리 id 유효성
    boolean isValidCategory(String categoryId);
}
