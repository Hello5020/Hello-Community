package com.hello.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hello.community.bean.Comment;
import com.hello.community.bean.Likemessage;
import com.hello.community.bean.Notification;
import com.hello.community.bean.Question;
import com.hello.community.enums.CommentTypeEnum;
import com.hello.community.enums.NotifitionEnum;
import com.hello.community.enums.NotifitionTypeEnum;
import com.hello.community.exception.CustomizeErrorCode;
import com.hello.community.exception.CustomizeException;
import com.hello.community.mapper.CommentMapper;
import com.hello.community.mapper.UserMapper;
import com.hello.community.service.CommentService;
import com.hello.community.service.LikemessageService;
import com.hello.community.mapper.LikemessageMapper;
import com.hello.community.service.NotificationService;
import com.hello.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author 25047
* @description 针对表【likemessage】的数据库操作Service实现
* @createDate 2022-06-08 08:15:05
*/
@Service
public class LikemessageServiceImpl extends ServiceImpl<LikemessageMapper, Likemessage>
    implements LikemessageService{

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    NotificationService notificationService;

    @Override
    @Transactional
    public void saveOrDeleteMessage(Likemessage likemessage) {
        QueryWrapper<Likemessage> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("parent_id", likemessage.getParentId()).eq("creator",likemessage.getCreator());
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",likemessage.getParentId());
        Comment dbComment = commentService.getOne(queryWrapper);
        Likemessage one = getOne(queryWrapper1);
        if (one != null) {
            if (one.getType().equals(CommentTypeEnum.Comment.getType())) {
                dbComment.setLikeCount(-1L);
                commentService.updateLikeCountById(dbComment);
            }else{
                Question question = questionService.getById(likemessage.getParentId());
                questionService.delLikeCount(question);
            }
            queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id",one.getId());
            remove(queryWrapper1);
            return;
        }
        if (likemessage.getParentId() == null || likemessage.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_PARAM_NOT_FOUND);
        }

        if (likemessage.getType() == null || !CommentTypeEnum.isExist(likemessage.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAMS_WRONG);
        }
        if (likemessage.getType().equals(CommentTypeEnum.Comment.getType())) {
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            save(likemessage);
            dbComment.setLikeCount(1L);
            commentService.updateLikeCountById(dbComment);
            Integer receiver = dbComment.getCommentator();
            createNotify(dbComment, receiver, NotifitionEnum.LIKE_COMMENT,"\""+ dbComment.getContent()+"\"",dbComment.getParentId());
        }else {
            Question question = questionService.getById(likemessage.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            save(likemessage);
            questionService.incLikeCount(question);
            createNotify(dbComment,question.getCreator(),NotifitionEnum.LIKE_QUESTION,question.getTitle(),question.getId().longValue());
        }
    }
    private void createNotify(Comment comment, Integer receiver, NotifitionEnum replyComment,String Title,Long parentId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(replyComment.getType());
        notification.setOuterid(parentId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotifitionTypeEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setOuterTitle(Title);
        notificationService.save(notification);
    }
}




