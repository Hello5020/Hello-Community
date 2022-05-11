package com.hello.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Comment;
import com.hello.community.bean.User;
import com.hello.community.dto.CommentCreateDTO;
import com.hello.community.dto.CommentDTO;
import com.hello.community.dto.ResultDTO;
import com.hello.community.enums.CommentTypeEnum;
import com.hello.community.exception.CustomizeErrorCode;
import com.hello.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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

        if(commentDTO == null || StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        Comment entity = new Comment();
        entity.setContent(commentDTO.getContent());
        entity.setParentId(commentDTO.getParentId());
        entity.setType(commentDTO.getType());
        entity.setGmtModified(System.currentTimeMillis());
        entity.setGmtCreate(System.currentTimeMillis());
        entity.setCommentator(user.getId());
        commentService.saveAndCheck(entity);
        return ResultDTO.successOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public  ResultDTO<List> comments(@PathVariable(name = "id")Integer id,
                               @RequestParam(value = "page",defaultValue = "1")Integer page){
        Integer size = 8;
        Page<Comment> pages = new Page<>(page, size);
        Page<Comment> page1 = commentService.page(pages, null);
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, page, size, CommentTypeEnum.Comment);
        return ResultDTO.successOf(commentDTOS);
    }

}
