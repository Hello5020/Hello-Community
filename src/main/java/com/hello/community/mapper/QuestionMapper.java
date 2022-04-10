package com.hello.community.mapper;
import java.util.List;

import com.hello.community.bean.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 25047
* @description 针对表【question】的数据库操作Mapper
* @createDate 2022-04-10 18:05:43
* @Entity com.hello.community.bean.Question
*/
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
    @Override
    int insert(Question question);

    List<Question> selectAll();
}




