package com.hello.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Question;
import com.hello.community.bean.User;
import com.hello.community.dto.QuestionDTO;
import com.hello.community.service.QuestionService;
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

    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "page",defaultValue = "1")Integer page){
        Integer size = 8;
        Page<Question> pages = new Page<>(page, size);
        Page<Question> page1 = questionService.page(pages, null);
        List<QuestionDTO> questionList = questionService.getAll(page,size);
        model.addAttribute("questions",questionList);
        model.addAttribute("pn",page1);
        return "index";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String main(User user,
                       HttpSession session,
                       Model model,
                       HttpServletResponse response){
        if(!StringUtils.isEmpty(user.getName()) && !StringUtils.isEmpty(user.getPassword())){
            session.setAttribute("loginUser",user);
            String token = UUID.randomUUID().toString();
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            model.addAttribute("msg","账号密码错误!");
            return "login";
        }
    }

}
