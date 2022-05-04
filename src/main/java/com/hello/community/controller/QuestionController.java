package com.hello.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Comment;
import com.hello.community.dto.CommentDTO;
import com.hello.community.dto.QuestionDTO;
import com.hello.community.enums.CommentTypeEnum;
import com.hello.community.service.CommentService;
import com.hello.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id,
                           @RequestParam(value = "page",defaultValue = "1")Integer page,
                           Model model){
        QuestionDTO question = questionService.getQuestionById(id);
        questionService.incView(id);
        Integer size = 8;
        Page<Comment> pages = new Page<>(page, size);
        Page<Comment> page1 = commentService.page(pages, null);
        List<CommentDTO> comments = commentService.listByTargetId(id,page,size,CommentTypeEnum.Question);
        model.addAttribute("question",question);
        model.addAttribute("comments",comments);
        return "question";
    }

}
