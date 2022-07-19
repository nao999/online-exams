package com.exam.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.TPaper;
import com.exam.entity.vo.PaperVO;
import com.exam.entity.vo.QuestionTableVO;
import com.exam.service.TPaperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-28
 */
@RestController
@RequestMapping("/paper")
@CrossOrigin
public class TPaperController {

    @Autowired
    private TPaperService paperService;

    Logger logger = LoggerFactory.getLogger(TPaperController.class);

    // 用户得到试卷
    @GetMapping("/getPapersByUsers")
    public RestResponse<List<PaperVO>> getPapersByUsers() {

        return null;
    }

    @DeleteMapping("/deletePaper/{id}")
    public RestResponse<String> deletePaper(@PathVariable("id") String id) {
        try {
            paperService.deletePaper(id);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @PutMapping("/editPaper")
    public RestResponse<String> editPaper(@RequestBody PaperVO paperVO) {
        try {
            paperService.editPaper(paperVO);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @PostMapping("/addPaper")
    public RestResponse<String> addPaper(@RequestBody PaperVO paperVO) {

        try {
            paperService.addPaper(paperVO);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok();
    }

    @GetMapping("/getPaperById/{id}")
    public RestResponse<PaperVO> getPaperById(@PathVariable("id") String id) {
        PaperVO paperVO = paperService.getPaperById(id);
        return RestResponse.ok(paperVO);
    }


    @GetMapping("/page/list")
    public RestResponse<Page<PaperVO>> pageList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "subject", required = false) String subject) {
        Page<PaperVO> paperList = null;
        try {
            paperList = paperService.getPageList(pageNum, pageSize, keyword, subject);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok(paperList);
    }
}

