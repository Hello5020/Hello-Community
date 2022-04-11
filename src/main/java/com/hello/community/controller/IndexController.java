package com.hello.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
                        Model model,
                        @RequestParam(value = "page",defaultValue = "1")Integer page){
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

        Page<Question> pages = new Page<>(page, 5);
        Page<Question> page1 = questionService.page(pages, null);
        List<QuestionDTO> questionList = questionService.getAll(page,5);
        model.addAttribute("questions",questionList);
        model.addAttribute("pn",page1);
        return "index";
    }
}
