package com.hello.community.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hello.community.bean.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 25047
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2022-04-16 18:57:58
* @Entity com.hello.community.bean.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    Page<Comment> selectList(@Param("page") Page<Comment> page,@Param(Constants.WRAPPER)Wrapper<Comment> queryWrapper);

    int updateCommentCountById(Comment comment);

    int updateLikeCountById(Comment comment);
}




