package com.exam.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName : ErrorListVO
 * @Description :
 * @Author : y
 * @Date: 2021-08-31 20:22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorListVO {
    private String id;

    @ApiModelProperty(value = "错题本学生id")
    private String studentId;

    @ApiModelProperty(value = "错题id")
    private String questionId;

    @ApiModelProperty(value = "学生答案")
    private String studentAnswer;

    @ApiModelProperty(value = "正确答案")
    private String rightAnswer;

    private QuestionVO questionContent;

    private Date createTime;
}
