package com.exam.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName : StatisticsInfo
 * @Description :
 * @Author : y
 * @Date: 2021-09-11 08:32
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsInfo {

    // 完成总题数
    private Integer totalQuestionNum;

    // 正确题数
    private Integer rightNum;

    // 平均用时
    private String aveTime;
}
