package com.exam.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName : StudentLogVO
 * @Description :
 * @Author : y
 * @Date: 2021-10-05 10:56
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentLogVO {
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "学生名称")
    private String studentName;

    @ApiModelProperty(value = "操作类型(1.登录 2.登出 3.快速开始 4.专项训练 5.智能组卷 6.提交任务)")
    private String operationType;

    @ApiModelProperty(value = "任务标题")
    private String taskId;

    @ApiModelProperty(value = "是否删除")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;
}
