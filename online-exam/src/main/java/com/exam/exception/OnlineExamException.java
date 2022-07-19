package com.exam.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName : OnlineExamException
 * @Description :
 * @Author : y
 * @Date: 2021-07-14 10:23
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineExamException extends RuntimeException{
    private Integer code;// 状态码

    private String msg;// 异常信息
}
