package com.hello.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hello.community.bean.User;
import com.hello.community.service.UserService;
import com.hello.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 25047
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-04-09 19:37:40
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public int insertUser(User user){
       return userMapper.insert(user);
    }

    @Override
    public User getUserByToken(String token) {
        return userMapper.selectByToken(token);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public void insertOrUpdateUser(User user) {
        User dbUser = userMapper.selectByAccountId(user.getAccountId());
        if (dbUser == null) {
            user.setGmtCreat(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreat());
            insertUser(user);
        }else{
            dbUser.setGmtModified(user.getGmtCreat());
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            userMapper.updateById(dbUser);
        }
    }

    @Override
    public User getUserByNameandPassword(String name, String password) {
        return userMapper.selectByNameAndPassword(name,password);
    }

    @Override
    public List<User> getUserByName(String name) {
        return userMapper.selectByName(name);
    }


}




