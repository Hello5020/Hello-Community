package com.hello.community.controller;

import com.hello.community.bean.Question;
import com.hello.community.bean.User;
import com.hello.community.service.QuestionService;
import com.hello.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    UserService userService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false)String title,
                            @RequestParam(value = "description",required = false)String description,
                            @RequestParam(value = "tag",required = false)String tag,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title == null || title == "") {
            model.addAttribute("error","标题不能为空");
            return "publish";
        }else if(description == null || description == ""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }else if(tag == null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        Question question = new Question();
        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie:
                    cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userService.getUserByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("loginUser",user);
                    }
                    break;
                }
            }
        }
        if(user == null){
            model.addAttribute("error","用户未登录,请先登录!");
            return "publish";
        }
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModifie(question.getGmtCreate());
        questionService.create(question);
        return "redirect:/";
    }
}
