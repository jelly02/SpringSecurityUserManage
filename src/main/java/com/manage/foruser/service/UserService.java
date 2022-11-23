package com.manage.foruser.service;

import com.manage.foruser.Mapper.UserMapper;
import com.manage.foruser.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor //final 객체 생성자 생성
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;

    //중복 아이디 체크
    public int idChk(String id) throws Exception {
        int result = userMapper.idChk(id);
        return result;
    }

    //회원가입 처리
    @Transactional
    public int insertMember(User user) {

        BCryptPasswordEncoder  encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");

        int result = userMapper.insertMember(user);
        System.out.println("여기 서비스인데요 회원가입 됐나요?**************"+"result값"+result);

        return result;
    }

    //로그인 처리
    public String loginCheck(String id, String password) {
        String role = userMapper.loginCheck(id,password);

        return role;
    }



    //Spring security login method
    @Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
    public User loadUserByUsername(String id) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        return userMapper.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException((id)));
    }
}
