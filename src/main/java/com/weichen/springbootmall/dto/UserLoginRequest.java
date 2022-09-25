package com.weichen.springbootmall.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

//接住前端所傳來的帳號跟密碼
public class UserLoginRequest {

    @NotBlank  //不可為NULL 以及空白
    @Email
    private String email;

    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
