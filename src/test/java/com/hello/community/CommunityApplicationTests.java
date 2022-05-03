package com.hello.community;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Question;
import com.hello.community.dto.CommentDTO;
import com.hello.community.mapper.QuestionMapper;
import com.hello.community.service.CommentService;
import com.hello.community.service.QuestionService;
import com.hello.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;


@SpringBootTest
@Slf4j
class CommunityApplicationTests {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        Page<Question> pages = new Page<>(1, 5);
        questionMapper.selectPage(pages,null);
        List<Question> questionList = pages.getRecords();
        questionList.forEach(System.out::println);
    }
    @Test
    void test(){
        Page<Question> pages = new Page<>(1, 5);
        questionMapper.selectByCreator(pages,4);
        List<Question> questionList = pages.getRecords();
        questionList.forEach(System.out::println);
    }

    @Test
    public void incView() {
        Question question = questionService.getById(1);
        question.setViewCount(1);
        questionMapper.updateViewCountById(question);
    }

    @Test
    public void test1(){
        List<CommentDTO> commentDTOS = commentService.listByQuestionId(1, 1, 8);
        commentDTOS.forEach(System.out::println);
    }

    @Test
    public void test2(){
            UUID uuid = UUID.randomUUID();
            String strUUID = uuid.toString();
            System.out.println(strUUID);
    }

    @Test
    public void test3(){
        System.out.println(userService.getUserByName("test"));
    }
}
