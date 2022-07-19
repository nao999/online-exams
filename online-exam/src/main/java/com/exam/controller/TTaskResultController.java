package com.exam.controller;


import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.vo.TaskResultVO;
import com.exam.service.TTaskResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-28
 */
@RestController
@RequestMapping("/taskResult")
@CrossOrigin
public class TTaskResultController {

    @Autowired
    private TTaskResultService taskResultService;

    @PostMapping("/saveTaskResult")
    public RestResponse<String> saveTaskResult(
            @RequestBody TaskResultVO taskResultVO){

        try {
            taskResultService.saveTaskResult(taskResultVO);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok();
    }
}

