package com.exam.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName : Editor
 * @Description : 用于富文本编辑器显示图片使用
 * @Author : y
 * @Date: 2021-07-26 19:46
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Editor {
    private String url;
    private String alt;
    private String href;



}
