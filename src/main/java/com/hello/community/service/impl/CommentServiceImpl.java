package com.hello.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hello.community.bean.Comment;
import com.hello.community.bean.Question;
import com.hello.community.bean.User;
import com.hello.community.dto.CommentDTO;
import com.hello.community.enums.CommentTypeEnum;
import com.hello.community.exception.CustomizeErrorCode;
import com.hello.community.exception.CustomizeException;
import com.hello.community.mapper.UserMapper;
import com.hello.community.service.CommentService;
import com.hello.community.mapper.CommentMapper;
import com.hello.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    UserMapper userMapper;

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
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentMapper.updateCommentCountById(parentComment);
        }else {
            Question question = questionService.getById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            save(comment);
            questionService.incCommentCount(question);
        }

    }



    @Override
    public List<CommentDTO> listByTargetId(Integer id, Integer page, Integer size, CommentTypeEnum type) {
        Page<Comment> pages = new Page<>(page,size);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id)
                .eq("type",type.getType()).orderByDesc("gmt_create");
        Page<Comment> commentPage = commentMapper.selectList(pages,queryWrapper);
        List<Comment> comments = commentPage.getRecords();
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator())
                .collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        List<User> users = userMapper.selectBatchIds(userIds);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<CommentDTO> commentDTOList = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOList;
    }
    @Override
    public Page<Comment> get(Integer id, Integer page, Integer size, CommentTypeEnum type)
    {
        Page<Comment> pages = new Page<>(page,size);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id)
                .eq("type",type.getType()).orderByDesc("gmt_create");
        Page<Comment> commentPage = commentMapper.selectList(pages,queryWrapper);
        return commentPage;
    }
}




