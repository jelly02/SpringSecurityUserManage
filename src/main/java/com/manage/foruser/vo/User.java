package com.manage.foruser.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String password;
    private String name;
    private String phone;
    private String role;
    private String education;
    private String isMajor;
}
