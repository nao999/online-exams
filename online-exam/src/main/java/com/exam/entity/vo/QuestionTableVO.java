package com.exam.entity.vo;

import com.exam.entity.TChoiceItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @ClassName : QuestionTableVO
 * @Description : 用于显示题目信息
 * @Author : y
 * @Date: 2021-07-23 21:33
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTableVO {
    private String id;
    private String level;
    private String levelName;
    private String subject;
    // 题目类型
    private String questionType;
    private String questionTypeName;

    private String questionContent;
    private Integer score;
    private Integer difficult;
    private Date createTime;
    // 题目选项
    private List<TChoiceItem> items;
}
