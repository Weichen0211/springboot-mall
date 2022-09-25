package com.weichen.springbootmall.service;

import com.weichen.springbootmall.dto.UserLoginRequest;
import com.weichen.springbootmall.dto.UserRegisterRequest;
import com.weichen.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);


}
