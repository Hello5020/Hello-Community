package com.hello.community.controller;

import com.hello.community.bean.Comment;
import com.hello.community.bean.User;
import com.hello.community.dto.CommentCreateDTO;
import com.hello.community.dto.ResultDTO;
import com.hello.community.exception.CustomizeErrorCode;
import com.hello.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentDTO,
                       HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment entity = new Comment();
        entity.setContent(commentDTO.getContent());
        entity.setParentId(commentDTO.getParentId());
        entity.setType(commentDTO.getType());
        entity.setGmtModified(System.currentTimeMillis());
        entity.setGmtCreate(System.currentTimeMillis());
        entity.setCommentator(1);
        commentService.save(entity);
        return ResultDTO.successOf();
    }

}
