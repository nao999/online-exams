package com.exam.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName : PaperItem
 * @Description :
 * @Author : y
 * @Date: 2021-07-28 19:48
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperItem {

    private String id;

    private Integer questionItemId;

    @ApiModelProperty(value = "题目分组标题")
    private String questionTitle;

    @ApiModelProperty(value = "总分")
    private Integer totalScore;

    @ApiModelProperty(value = "题目数量")
    private Integer questionNum;

    private List<QuestionVO> questionContent;
}
