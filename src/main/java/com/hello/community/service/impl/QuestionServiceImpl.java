package com.hello.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hello.community.bean.Question;
import com.hello.community.bean.User;
import com.hello.community.dto.QuestionDTO;
import com.hello.community.exception.CustomizeErrorCode;
import com.hello.community.exception.CustomizeException;
import com.hello.community.mapper.UserMapper;
import com.hello.community.service.QuestionService;
import com.hello.community.mapper.QuestionMapper;
import com.hello.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 25047
* @description 针对表【question】的数据库操作Service实现
* @createDate 2022-04-10 18:05:43
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserService userService;

    @Override
    public void create(Question question) {
        questionMapper.insert(question);
    }

    @Override
    public List<QuestionDTO> getAll(Integer page,Integer size) {
        Page<Question> pages = new Page<>(page, size);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("gmt_create");
        questionMapper.selectPage(pages,queryWrapper);
        List<Question> questionList = pages.getRecords();
        List<QuestionDTO> questions = new ArrayList<>();
        for (Question question:
             questionList) {
            User user = userService.getUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questions.add(questionDTO);
        }
        return questions;
    }

    @Override
    public List<QuestionDTO> getAll(Integer id, Integer page, Integer size) {
        Page<Question> pages = new Page<>(page, size);
        questionMapper.selectByCreator(pages, id);
        List<Question> questionList = pages.getRecords();
        List<QuestionDTO> questions = new ArrayList<>();
        for (Question question:
                questionList) {
            User user = userService.getUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questions.add(questionDTO);
        }
        return questions;
    }

    @Override
    public List<QuestionDTO> getAll(String searchText, Integer page, Integer size) {
        Page<Question> pages = new Page<>(page, size);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("title",searchText);
        queryWrapper.orderByDesc("gmt_create");
        questionMapper.selectPage(pages,queryWrapper);
        List<Question> questionList = pages.getRecords();
        List<QuestionDTO> questions = new ArrayList<>();
        for (Question question:
                questionList) {
            User user = userService.getUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questions.add(questionDTO);
        }
        return questions;
    }

    @Override
    public Page<Question> getPageByCreator(Page<Question> page, Integer creator) {
        return questionMapper.selectByCreator(page,creator);
    }

    @Override
    public QuestionDTO getQuestionById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = getById(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userService.getUserById(question.getCreator());
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModifie(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            create(question);
        }else{
            question.setGmtModifie(question.getGmtCreate());
            questionMapper.updateById(question);
        }
    }

    @Override
    public void incView(Integer id) {
        Question question = getById(id);
        question.setViewCount(1);
        int i = questionMapper.updateViewCountById(question);
        if (i == 0) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
    }

    @Override
    public void incCommentCount(Question question) {
        question.setCommentCount(1);
        questionMapper.updateCommentCountById(question);
    }

    @Override
    public List<QuestionDTO> getQuestionsByTags(QuestionDTO qu) {
        if(StringUtils.isBlank(qu.getTag())){
            return new ArrayList<>();
        }
        String[] tag = StringUtils.split(qu.getTag(), ",");
        String regexpTag = Arrays.stream(tag).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(qu.getId());
        question.setTag(regexpTag);
        List<Question> questionList = questionMapper.selectByTag(question);
        List<QuestionDTO> collect = questionList.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<QuestionDTO> getHotQuestion(Integer size) {
        Page<Question> pages = new Page<>(1, size);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("view_count");
        questionMapper.selectPage(pages,queryWrapper);
        List<Question> questionList = pages.getRecords();
        List<QuestionDTO> questions = new ArrayList<>();
        for (Question question:
                questionList) {
            User user = userService.getUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questions.add(questionDTO);
        }
        return questions;
    }

}




