package com.hello.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Notification;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hello.community.bean.User;
import com.hello.community.dto.NotificationDTO;

import java.util.List;

/**
* @author 25047
* @description 针对表【notification】的数据库操作Service
* @createDate 2022-05-25 22:21:40
*/
public interface NotificationService extends IService<Notification> {

    Page<Notification> getPageByID(Page<Notification> page,Integer id);
    List<NotificationDTO> list(Integer id, Integer page, Integer size);

    Long unreadCount(Integer id);

    NotificationDTO read(Integer id, User user);
}
