package com.hello.community;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hello.community.bean.Question;
import com.hello.community.mapper.QuestionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    QuestionMapper questionMapper;

    @Test
    void contextLoads() {
        Page<Question> pages = new Page<>(1, 5);
        questionMapper.selectPage(pages,null);
        List<Question> questionList = pages.getRecords();
        questionList.forEach(System.out::println);
    }

}
