package com.hello.community.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Notification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hello.community.bean.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 25047
* @description 针对表【notification】的数据库操作Mapper
* @createDate 2022-05-25 22:21:40
* @Entity com.hello.community.bean.Notification
*/
@Mapper
public interface NotificationMapper extends BaseMapper<Notification> {
    Page<Notification> selectById(@Param("page") Page<Notification> page, @Param("receiverId") Integer receiverId);

}




