package com.exam.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName : QuestionItem
 * @Description :
 * @Author : y
 * @Date: 2021-07-23 16:37
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionItem {
    private String prefix;
    private String content;
}
