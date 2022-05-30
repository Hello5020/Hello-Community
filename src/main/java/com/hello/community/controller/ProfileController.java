package com.hello.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Notification;
import com.hello.community.bean.Question;
import com.hello.community.bean.User;
import com.hello.community.dto.NotificationDTO;
import com.hello.community.dto.QuestionDTO;
import com.hello.community.service.NotificationService;
import com.hello.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;

    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(
            HttpSession session,
            @PathVariable(name = "action")String action,
                          Model model,
            @RequestParam(value = "page",defaultValue = "1")Integer page){
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/";
        }
        Integer size = 8;
        if ("questions".equals(action)) {
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            Page<Question> pages = new Page<>(page, size);
            Page<Question> page1 = questionService.getPageByCreator(pages,user.getId());
            List<QuestionDTO> questionList = questionService.getAll(user.getId(),page,size);
            model.addAttribute("questions",questionList);
            model.addAttribute("pn",page1);
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            Page<Notification> pages = new Page<>(page, size);
            Page<Notification> page1 = notificationService.getPageByID(pages,user.getId());
            List<NotificationDTO> notificationDTOS = notificationService.list(user.getId(), page, size);
            model.addAttribute("notifications",notificationDTOS);
            model.addAttribute("pn",page1);
        }
        Long unreadCount = notificationService.unreadCount(user.getId());
        model.addAttribute("unreadCount",unreadCount);
        return "profile";
    }
}
