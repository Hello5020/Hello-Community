package com.hello.community.mapper;
import java.io.Serializable;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.hello.community.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 25047
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-04-09 19:37:40
* @Entity com.hello.community.bean.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Override
    int insert(User user);

    User selectByToken(@Param("token") String token);

    @Override
    User selectById(Serializable id);
}




