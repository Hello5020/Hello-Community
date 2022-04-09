package com.hello.community.controller;

import com.hello.community.bean.User;
import com.hello.community.mapper.UserMapper;
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

    @GetMapping("/")
    public String hello(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
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
        return "index";
    }
}
