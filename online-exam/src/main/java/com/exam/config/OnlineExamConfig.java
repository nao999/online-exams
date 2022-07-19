package com.exam.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testng.annotations.IConfigurationAnnotation;

/**
 * @ClassName : OnlineExamConfig
 * @Description :
 * @Author : y
 * @Date: 2021-07-11 08:33
 */

@Configuration
public class OnlineExamConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }


}
