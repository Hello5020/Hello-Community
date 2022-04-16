package com.hello.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hello.community.dto.QuestionDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 25047
* @description 针对表【question】的数据库操作Service
* @createDate 2022-04-10 18:05:43
*/
public interface QuestionService extends IService<Question> {

    void create(Question question);
    List<QuestionDTO> getAll(Integer page,Integer size);
    List<QuestionDTO> getAll(Integer id,Integer page,Integer size);
    Page<Question> getPageByCreator(Page<Question> page,Integer creator);
    QuestionDTO getQuestionById(Integer id);
    void createOrUpdate(Question question);
    void incView(Integer id);
    void incCommentCount(Question question);
}
