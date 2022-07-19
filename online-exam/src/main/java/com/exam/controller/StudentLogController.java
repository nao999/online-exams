package com.exam.controller;


import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.StudentLog;
import com.exam.entity.vo.StudentLogVO;
import com.exam.service.StudentLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-04
 */
@RestController
@RequestMapping("/studentLog")
@CrossOrigin
public class StudentLogController {

    @Autowired
    private StudentLogService studentLogService;

    @GetMapping("/getLogInfo")
    public RestResponse<List<StudentLogVO>> getLogInfo(){
        List<StudentLogVO> studentLogList = null;
        try {
            studentLogList = studentLogService.getLogInfo();
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(studentLogList);

    }
}

