package com.hello.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Notification;
import com.hello.community.bean.Question;
import com.hello.community.bean.User;
import com.hello.community.dto.NotificationDTO;
import com.hello.community.dto.QuestionDTO;
import com.hello.community.enums.NotifitionEnum;
import com.hello.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpSession session,
                          @PathVariable(name = "id")Integer id){
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/";
        }
       NotificationDTO notificationDTO = notificationService.read(id,user);
        if (NotifitionEnum.REPLY_COMMENT.getType()==notificationDTO.getType()
                || NotifitionEnum.REPLY_QUESTION.getType()==notificationDTO.getType()
                || NotifitionEnum.LIKE_QUESTION.getType()==notificationDTO.getType()
                || NotifitionEnum.LIKE_COMMENT.getType()==notificationDTO.getType()){
            return "redirect:/question/"+ notificationDTO.getOuterid();
        }else {
            return "redirect:/";
        }
    }
}
