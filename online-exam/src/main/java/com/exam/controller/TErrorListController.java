package com.exam.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.vo.ErrorListVO;
import com.exam.exception.OnlineExamException;
import com.exam.service.TErrorListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-31
 */
@RestController
@RequestMapping("/errorList")
@CrossOrigin
public class TErrorListController {

    @Autowired
    private TErrorListService errorListService;


    @GetMapping("/page/list")
    public RestResponse<Page<ErrorListVO>> pageList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "keyword",required = false) String keyword,
            @RequestParam(value = "subject",required = false) String subject){
        Page<ErrorListVO> errorList = null;
        try {
            errorList = errorListService.pageList(pageNum, pageSize, keyword,subject);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(),SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(errorList);
    }

    @PostMapping("/addErrorQuestion")
    public RestResponse<String> addErrorQuestion(@RequestBody ErrorListVO errorListVO){
        try {
            errorListService.saveErrorQuestion(errorListVO);
        } catch (OnlineExamException e) {
            return RestResponse.fail(e.getCode(), e.getMsg());
        }
        return RestResponse.ok();
    }
}

