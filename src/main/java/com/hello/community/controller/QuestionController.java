package com.hello.community.controller;

import com.hello.community.dto.QuestionDTO;
import com.hello.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id,
                           Model model){
        QuestionDTO question = questionService.getQuestionById(id);
        questionService.incView(id);
        model.addAttribute("question",question);
        return "question";
    }

}
