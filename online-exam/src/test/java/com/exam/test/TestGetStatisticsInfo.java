package com.exam.test;

import com.exam.service.TExerciseRecordsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : TestGetStatisticsInfo
 * @Description :
 * @Author : y
 * @Date: 2021-09-12 16:52
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGetStatisticsInfo {

    @Autowired
    private TExerciseRecordsService exerciseRecordsService;

    @Test
    public void testGetStatisticsInfo(){
        exerciseRecordsService.getStatisticsInfo("");
    }
}
