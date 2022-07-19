package com.exam.controller;

import com.exam.base.RestResponse;
import com.exam.config.OssProperties;
import com.exam.entity.vo.Editor;
import com.exam.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : UploafController
 * @Description :
 * @Author : y
 * @Date: 2021-07-26 17:28
 */

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {
    @Autowired
    private OssProperties ossProperties;

    @PostMapping("/uploadImg")
    public Map<String, Object> uploadImg(@RequestParam("image") MultipartFile uploadImg) throws IOException {

        RestResponse<String> result = Utils.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                uploadImg.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                uploadImg.getOriginalFilename());

        Map<String, Object> map = new HashMap<>();
        map.put("errno", 0);
        List<Editor> editors = new ArrayList<>();
        Editor editor = new Editor(result.getResponse(),"","");
        editors.add(editor);
        map.put("data", editors);
        return map;
    }
}
