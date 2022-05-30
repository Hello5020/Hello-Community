package com.hello.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hello.community.bean.Notification;
import com.hello.community.bean.Question;
import com.hello.community.bean.User;
import com.hello.community.dto.NotificationDTO;
import com.hello.community.dto.QuestionDTO;
import com.hello.community.enums.NotifitionEnum;
import com.hello.community.enums.NotifitionTypeEnum;
import com.hello.community.exception.CustomizeErrorCode;
import com.hello.community.exception.CustomizeException;
import com.hello.community.exception.ICustomizeErrorCode;
import com.hello.community.service.NotificationService;
import com.hello.community.mapper.NotificationMapper;
import com.hello.community.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 25047
* @description 针对表【notification】的数据库操作Service实现
* @createDate 2022-05-25 22:21:40
*/
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification>
    implements NotificationService{

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    UserService userService;

    @Override
    public Page<Notification> getPageByID(Page<Notification> page, Integer id) {
        return notificationMapper.selectById(page,id);
    }

    @Override
    public List<NotificationDTO> list(Integer id, Integer page, Integer size) {
        Page<Notification> pages = new Page<>(page, size);
        getPageByID(pages,id);
        List<Notification> notificationList = pages.getRecords();
        List<NotificationDTO> notifications = new ArrayList<>();
        for (Notification notification:
                notificationList) {
            User user = userService.getUserById(notification.getNotifier());
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setNotifier(user);
            notificationDTO.setTypeName(NotifitionEnum.nameOfType(notification.getType()));
            notifications.add(notificationDTO);
        }
        return notifications;
    }

    @Override
    public Long unreadCount(Integer id) {
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver",id).eq("status",NotifitionTypeEnum.UNREAD.getStatus());
        return notificationMapper.selectCount(queryWrapper);
    }

    @Override
    public NotificationDTO read(Integer id, User user) {
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Notification notification = notificationMapper.selectOne(queryWrapper);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getReceiver().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotifitionTypeEnum.READ.getStatus());
        notificationMapper.updateById(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setNotifier(user);
        notificationDTO.setTypeName(NotifitionEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}




