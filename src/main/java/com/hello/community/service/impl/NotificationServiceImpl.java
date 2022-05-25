package com.hello.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hello.community.bean.Notification;
import com.hello.community.service.NotificationService;
import com.hello.community.mapper.NotificationMapper;
import org.springframework.stereotype.Service;

/**
* @author 25047
* @description 针对表【notification】的数据库操作Service实现
* @createDate 2022-05-25 22:21:40
*/
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification>
    implements NotificationService{

}




