package com.weichen.springbootmall.dao;

import com.weichen.springbootmall.dto.UserRegisterRequest;
import com.weichen.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
