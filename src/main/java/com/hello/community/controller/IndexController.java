package com.hello.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Question;
import com.hello.community.dto.QuestionDTO;
import com.hello.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
}
