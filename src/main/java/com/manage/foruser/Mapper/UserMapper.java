package com.manage.foruser.Mapper;

import com.manage.foruser.vo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    //회원가입
    int insertMember(User user);

    //로그인
    String loginCheck(String id, String password);
}
