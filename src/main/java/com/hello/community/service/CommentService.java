package com.hello.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hello.community.dto.CommentDTO;
import com.hello.community.enums.CommentTypeEnum;
import com.hello.community.enums.NotifitionEnum;

import java.util.List;

/**
* @author 25047
* @description 针对表【comment】的数据库操作Service
* @createDate 2022-04-16 18:57:58
*/
public interface CommentService extends IService<Comment> {

    void saveAndCheck(Comment comment);

    List<CommentDTO> listByTargetId(Integer id, Integer page, Integer size, CommentTypeEnum type);

    Page<Comment> get(Integer id, Integer page, Integer size, CommentTypeEnum type);

    void updateLikeCountById(Comment dbComment);
}
