package com.exam.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : PointEditVO
 * @Description : 用于显示知识点的VO类
 * @Author : y
 * @Date: 2021-07-21 17:00
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointEditVO {
    private String id;

    private String value;

    private String label;

    private Integer level;

    // 知识点对应的题目总数
    private Integer questionNumber;

    // 已经做了的题目总数
    private Integer doneQuestionNumber;

    private List<PointEditVO> children = new ArrayList<>();
}
