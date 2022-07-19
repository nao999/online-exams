package com.exam.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : PaperVO
 * @Description :
 * @Author : y
 * @Date: 2021-07-28 19:42
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperVO {

    private String id;

    @ApiModelProperty(value = "试卷属于的年级")
    private String level;

    @ApiModelProperty(value = "试卷所属的科目id")
    private String subject;

    @ApiModelProperty(value = "试卷名称")
    private String paperTitle;

    @ApiModelProperty(value = "试卷建议时长")
    private String time;

    List<PaperItem> questionItems = new ArrayList<>();

    private String createTime;
}
