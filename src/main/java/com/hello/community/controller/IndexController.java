package com.hello.community.controller;

import com.hello.community.bean.Question;
import com.hello.community.bean.User;
import com.hello.community.dto.QuestionDTO;
import com.hello.community.mapper.UserMapper;
import com.hello.community.service.QuestionService;
import com.hello.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller

public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        Model model){
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie:
                    cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userService.getUserByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("loginUser",user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionList = questionService.getAll();
        model.addAttribute("questions",questionList);

        return "index";
    }
}
