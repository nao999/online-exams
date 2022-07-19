package com.exam.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName : ExerciseRecordVO
 * @Description :
 * @Author : y
 * @Date: 2021-08-05 09:58
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseRecordVO {
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "训练类型(0 快速开始,1 专项练习,2 智能组卷)")
    private Integer exerciseType;

    @ApiModelProperty(value = "训练类型名字")
    private String exerciseTypeName;

    @ApiModelProperty(value = "训练所属的学科")
    private String subjectId;

    @ApiModelProperty(value = "对应的题目列表")
    private List<QuestionVO> questionList;

    @ApiModelProperty(value = "正确个数")
    private Integer correctNum;

    @ApiModelProperty(value = "练习用时")
    private String time;

    private List<ChooseItem> chooseAnswer;

    private String createTime;
}
