package com.manage.foruser.service;

import com.manage.foruser.Mapper.UserMapper;
import com.manage.foruser.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserMapper userMapper;

    public UserService (UserMapper _userMapper){
        this.userMapper = _userMapper;
    }

    //회원가입 처리
    @Transactional
    public int insertMember(User user) {
        user.setRole("USER");
        int result = this.userMapper.insertMember(user);

        return result;
    }

    //로그인 처리
    public String loginCheck(String id, String password) {
        String role = userMapper.loginCheck(id,password);

        return role;
    }
}
