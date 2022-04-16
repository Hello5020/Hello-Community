package com.hello.community.mapper;

import com.hello.community.bean.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 25047
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2022-04-16 18:57:58
* @Entity com.hello.community.bean.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




