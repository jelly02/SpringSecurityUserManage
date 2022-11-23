package com.manage.foruser.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
public class User implements UserDetails {

    private String id;
    private String password;
    private String name;
    private String phone;
    private String role;
    private String education;
    private String isMajor;

    @Builder
    public User (String id, String password,String name,String phone, String role, String education, String isMajor){
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.education = education;
        this.isMajor = isMajor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : role.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부
    @Override
    public boolean isAccountNonLocked() {
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부
    @Override
    public boolean isEnabled() {
        return true; // true -> 사용 가능
    }
}
