package com.exam.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName : QuestionAnswer
 * @Description :
 * @Author : y
 * @Date: 2021-08-28 17:19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswer {
    // 回答题目的id
    private String questionId;

    // 回答内容
    private List<String> answer;
}
