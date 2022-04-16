package com.hello.community.service;

import com.hello.community.bean.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 25047
* @description 针对表【comment】的数据库操作Service
* @createDate 2022-04-16 18:57:58
*/
public interface CommentService extends IService<Comment> {

    void saveAndCheck(Comment comment);
}
