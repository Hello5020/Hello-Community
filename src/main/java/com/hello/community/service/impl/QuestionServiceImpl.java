package com.hello.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hello.community.bean.Question;
import com.hello.community.bean.User;
import com.hello.community.dto.QuestionDTO;
import com.hello.community.mapper.UserMapper;
import com.hello.community.service.QuestionService;
import com.hello.community.mapper.QuestionMapper;
import com.hello.community.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<QuestionDTO> getAll() {
        List<Question> questionList = questionMapper.selectAll();
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




