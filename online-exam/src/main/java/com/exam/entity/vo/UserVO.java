package com.exam.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @ClassName : StudentVO
 * @Description :
 * @Author : y
 * @Date: 2021-07-16 15:42
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private String id;

    private Integer userUuid;
    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "学生年级(1-12)")
    private Integer userLevel;

    @ApiModelProperty(value = "所处班级")
    private Integer userClass;

    @ApiModelProperty(value = "0.男 1.女")
    private String sex;

    private Integer age;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birthDay;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "1.启用 2.禁用")
    private Integer status;

    private String imagePath;
}
