package com.weichen.springbootmall.service.impl;

import com.weichen.springbootmall.dao.UserDao;
import com.weichen.springbootmall.dto.UserRegisterRequest;
import com.weichen.springbootmall.model.User;
import com.weichen.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
