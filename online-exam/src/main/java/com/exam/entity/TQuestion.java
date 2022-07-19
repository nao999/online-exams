package com.exam.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TQuestion对象", description="")
public class TQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "1.单选题 2.多选题 3.判断题 4.填空题 5.简答题")
    private Integer questionType;

    @ApiModelProperty(value = "问题类型名称")
    private String questionTypeName;

    @ApiModelProperty(value = "学科id")
    private String subject;

    @ApiModelProperty(value = "题目分数")
    private Integer score;

    @ApiModelProperty(value = "年级")
    private Integer level;

    @ApiModelProperty(value = "题目难度")
    private Integer difficult;

    @ApiModelProperty(value = "出题人id")
    private String createUser;

    @ApiModelProperty(value = "试题内容")
    private String questionContent;

    @ApiModelProperty(value = "正确答案")
    private String rightAnswer;

    @ApiModelProperty(value = "答案分析")
    private String analysis;

    @ApiModelProperty(value = "知识点id")
    private String knowledgePoint;

    @ApiModelProperty(value = "是否删除")
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;


}