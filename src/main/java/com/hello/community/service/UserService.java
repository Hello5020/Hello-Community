package com.hello.community.service;

import com.hello.community.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 25047
* @description 针对表【user】的数据库操作Service
* @createDate 2022-04-09 19:37:40
*/

public interface UserService extends IService<User> {

    int insertUser(User user);

    User getUserByToken(String token);

    User getUserById(Integer id);
}
