package com.exam.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName : TaskResultVO
 * @Description :
 * @Author : y
 * @Date: 2021-08-28 08:44
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResultVO {

    private String id;

    private String taskId;

    @ApiModelProperty(value = "完成任务学生的id")
    private String studentId;

    private List<QuestionAnswer> answer;

}
