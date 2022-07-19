package com.exam.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName : PointVO
 * @Description :
 * @Author : y
 * @Date: 2021-07-21 10:49
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointVO {
    private String id;
    private String name;
    private Integer level;
    private String levelName;
    private Integer pointNumber;
}
