package com.study.connection.service;

import com.study.connection.mapper.MyBatisInitializer;
import com.study.connection.mapper.ValidationMapper;
import org.apache.ibatis.session.SqlSession;

public class ValidationService {

    private final SqlSession session;
    private final ValidationMapper validationMapper;

    public ValidationService() {
        this.session = MyBatisInitializer.getSqlSession();;
        this.validationMapper = session.getMapper(ValidationMapper.class);
    }

    public boolean validateInsertData(int categoryId, String writer, String password, String passwordConfirm, String title, String content) {
        System.out.println("유효성 검증 서비스 로직 필요");
        // DB 관련
        //
        return true;
    }

    public boolean validateUpdateData(int categoryId, String writer, String password, String title, String content) {
        System.out.println("유효성 검증 서비스 로직 필요");
        // DB 관련
        //
        return true;
    }

    public boolean isCategoryExist(int categoryId) {
        return true;
    }

    public boolean isPasswordMatch(String password) {
        return true;
    }

}
