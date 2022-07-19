package com.exam.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName : OssConfig
 * @Description :
 * @Author : y
 * @Date: 2021-07-26 19:18
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {
    private String endPoint;

    private String bucketName;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketDomain;
}
