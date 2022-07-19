package com.exam.test;

import com.exam.service.TQuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : TestQuestion
 * @Description :
 * @Author : y
 * @Date: 2021-07-24 09:42
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestQuestion {
    @Autowired
    private TQuestionService questionService;

    @Test
    public void testGetPageList(){
//        questionService.getPageList(1, 3, "");
    }
}
