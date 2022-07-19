package com.exam.entity.vo;

import com.exam.entity.TChoiceItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @ClassName : QuestionVO
 * @Description : 用于接收题库问题
 * @Author : y
 * @Date: 2021-07-23 16:49
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVO {
    private String id;

    @ApiModelProperty(value = "1.单选题 2.多选题 3.判断题 4.填空题 5.简答题")
    private Integer questionType;

    private String questionTypeName;

    @ApiModelProperty(value = "学科id")
    private String subject;

    @ApiModelProperty(value = "题目分数")
    private Integer score;

    @ApiModelProperty(value = "年级")
    private Integer level;

    private String levelName;

    @ApiModelProperty(value = "题目难度")
    private Integer difficult;

    @ApiModelProperty(value = "出题人id")
    private String createUser;

    @ApiModelProperty(value = "试题内容")
    private String questionContent;

    @ApiModelProperty(value = "正确答案")
    private String rightAnswer;

    @ApiModelProperty(value = "答案分析")
    private String analysis;

    @ApiModelProperty(value = "知识点id")
    private List<List<String>> point;

    // 用于展示的知识点
    private List<String> pointList;

    // 选项
    private List<TChoiceItem> items;

}
