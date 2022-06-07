package com.hello.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Question;
import com.hello.community.bean.User;
import com.hello.community.dto.QuestionDTO;
import com.hello.community.service.NotificationService;
import com.hello.community.service.QuestionService;
import com.hello.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(value = "page",defaultValue = "1")Integer page,
                        @RequestParam(value = "search",required = false)String search){
        Integer size = 8;
        Page<Question> pages = new Page<>(page, size);
        Page<Question> page1 = questionService.page(pages, null);
        List<QuestionDTO> questionList = questionService.getAll(page,size);
        if (search != null) {
            questionList = questionService.getAll(search,page,size);
        }
        List<QuestionDTO> hotQuestionList = questionService.getHotQuestion(size);
        model.addAttribute("questions",questionList);
        model.addAttribute("hotQuestions",hotQuestionList);
        model.addAttribute("pn",page1);
        return "index";
    }
    @GetMapping("/login")
    public String loginPage(Model model){
        return "login";
    }

    @PostMapping("/login")
    public String main(User user,
                       HttpSession session,
                       Model model,
                       HttpServletResponse response){
        if (session.getAttribute("smsg") != null) {
            session.removeAttribute("smsg");
        }
        model.addAttribute("name",user.getName());
        model.addAttribute("password",user.getPassword());
        List<User> userChecks = userService.getUserByName(user.getName());
        User userCheckAll = userService.getUserByNameandPassword(user.getName(), user.getPassword());
        if (user.getName() == null || user.getName() == ""
                || user.getPassword() == "" ||user.getPassword() == null ) {
            model.addAttribute("msg","用户名或密码不能为空!");
            return loginPage(model);
        }
        if(userChecks.size() == 0){
            model.addAttribute("msg","用户名输入错误,此用户名用户不存在!");
            return loginPage(model);
        }
        if(userCheckAll!=null){
            String token = UUID.randomUUID().toString();
            response.addCookie(new Cookie("token",token));
            user.setAccountId(userCheckAll.getAccountId());
            user.setToken(token);
            user.setAvatarUrl(userCheckAll.getAvatarUrl());
            userService.insertOrUpdateUser(user);
            session.setAttribute("loginUser",user);
            return hello(model,1,null);
        }else {
            model.addAttribute("msg","账号密码错误!");
            return loginPage(model);
        }
    }

}
