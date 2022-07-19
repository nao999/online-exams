package com.exam.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName : PredictScoreVO
 * @Description : 4
 * @Author : y
 * @Date: 2021-09-21 16:48
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredictScoreVO  {
    @ApiModelProperty(value = "预测分数")
    private Integer score;

    private String time;
}
