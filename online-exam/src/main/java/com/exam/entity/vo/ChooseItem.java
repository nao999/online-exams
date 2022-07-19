package com.exam.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName : ChooseItem
 * @Description :
 * @Author : y
 * @Date: 2021-08-05 10:32
 */

// 用户选项保存
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChooseItem {
    private String questionId;
    private List<String> prefix;
    // 是否选择正确，0为错误，1位正确
    private Integer correctOrNot;
}
