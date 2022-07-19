package com.exam.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName : TaskVO
 * @Description :
 * @Author : y
 * @Date: 2021-08-11 12:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskVO{
    private String id;

    @ApiModelProperty(value = "任务所属的年级")
    private Integer level;

    private String levelName;

    @ApiModelProperty(value = "任务的标题")
    private String title;

    @ApiModelProperty(value = "试卷id")
    private String paper;

    // 截止时间
    private Date deadline;



    // 表示当前学生此任务的状态，0 未完成，1 待批改，2 完成任务
    private Integer status;

    // 试卷总分
    private Integer score = 100;

    // 建议时长
    private String time;

    private Date createTime;
}
