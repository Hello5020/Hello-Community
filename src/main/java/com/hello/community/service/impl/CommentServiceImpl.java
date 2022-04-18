package com.hello.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hello.community.bean.Comment;
import com.hello.community.bean.Question;
import com.hello.community.enums.CommentTypeEnum;
import com.hello.community.exception.CustomizeErrorCode;
import com.hello.community.exception.CustomizeException;
import com.hello.community.service.CommentService;
import com.hello.community.mapper.CommentMapper;
import com.hello.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author 25047
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2022-04-16 18:57:58
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentMapper commentMapper;

    @Override
    @Transactional
    public void saveAndCheck(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAMS_WRONG);
        }
        if (comment.getType().equals(CommentTypeEnum.Comment.getType())) {
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            save(comment);
        }else {
            Question question = questionService.getById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            save(comment);
            question.setCommentCount(1);
            questionService.incCommentCount(question);
        }

    }
}




