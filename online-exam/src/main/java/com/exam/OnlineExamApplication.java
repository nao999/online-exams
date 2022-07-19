package com.exam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName : OnlineExamApplication
 * @Description :
 * @Author : y
 * @Date: 2021-07-10 16:12
 */

@MapperScan("com.exam.mapper")
@SpringBootApplication
public class OnlineExamApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineExamApplication.class, args);
    }
}
