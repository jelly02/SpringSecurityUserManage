package com.manage.foruser;

import com.manage.foruser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity //Security 활성화
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { 

    private final UserService userService;

    //Security 인증 무시
    @Override
    public void configure(WebSecurity web) { 
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    // HTTP 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception { 
        http
                .authorizeRequests() // 6
                .antMatchers("/","/login", "/signup", "/user").permitAll() // 누구나 접근 허용
                .antMatchers("/main").hasRole("USER") // USER, ADMIN만 접근 가능
                .antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                .and()
                .formLogin() // 7
                .loginPage("/login") // 로그인 페이지 링크
                .defaultSuccessUrl("/loginSuccess") // 로그인 성공 후 리다이렉트 주소
                .and()
                .logout() // 8
                .logoutSuccessUrl("/login") // 로그아웃 성공시 리다이렉트 주소
                .invalidateHttpSession(true) // 세션 날리기
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 9
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
}