package com.weichen.springbootmall.service.impl;

import com.weichen.springbootmall.dao.UserDao;
import com.weichen.springbootmall.dto.UserLoginRequest;
import com.weichen.springbootmall.dto.UserRegisterRequest;
import com.weichen.springbootmall.model.User;
import com.weichen.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    //創建log變數，查看歷程
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 檢查註冊email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if(user != null){
            log.warn("該 email {} 已被註冊",userRegisterRequest.getEmail()); //參數會放入{}內
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);  //前端請求到這停止，丟EXCEPTION出去
        }

        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {

        User user  = userDao.getUserByEmail(userLoginRequest.getEmail());

        // email 尚未註冊
        if(user == null){
            log.warn("該 email {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 檢查密碼是否一致
        if(user.getPassword().equals(userLoginRequest.getPassword())){
            return user;
        }else{
            log.warn("email {} 密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
