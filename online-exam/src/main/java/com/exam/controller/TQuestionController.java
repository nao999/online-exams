package com.exam.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.*;
import com.exam.entity.vo.*;
import com.exam.service.KnowledgePointToQuestionService;
import com.exam.service.TChoiceItemService;
import com.exam.service.TQuestionService;
import com.exam.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-22
 */
@RestController
@RequestMapping("/question")
@CrossOrigin
public class TQuestionController {

    Logger logger = LoggerFactory.getLogger(TQuestionController.class);

    @Autowired
    private TChoiceItemService choiceItemService;

    @Autowired
    private TQuestionService questionService;

    @Autowired
    private KnowledgePointToQuestionService knowledgePointToQuestionService;



    // 学生训练时得到题目
    @GetMapping("/getExerciseQuestions")
    public RestResponse<List<QuestionVO>> getExerciseQuestions(
            @RequestParam(value = "subjectId",required = false) String subjectId,
            @RequestParam(value = "pointId",required = false) String pointId,
            @RequestParam(value = "exerciseType",required = false) Integer exerciseType){
        // 得到当前用户id
//        String userId = Utils.getCurrentUserId();
        List<QuestionVO> questionVOList = null;
        try {
            questionVOList = questionService.getExerciseQuestion(subjectId,pointId,exerciseType);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(questionVOList);
    }

    @DeleteMapping("/deleteQuestion/{id}")
    public RestResponse<String> deleteQuestion(@PathVariable("id") String id){
        try {
            questionService.deleteQuestion(id);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @PutMapping("/editQuestion")
    public RestResponse<String> editQuestion(@RequestBody QuestionVO questionVO){
        try {
            questionService.updateQuestion(questionVO);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @PostMapping("/addQuestion")
    public RestResponse<String> addQuestion(
            @RequestBody QuestionVO question){

        try {
            questionService.saveQuestion(question);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok();
    }

    @GetMapping("/getQuestionById/{id}")
    public RestResponse<QuestionVO> getQuestionById(@PathVariable("id") String id){
        QuestionVO questionVO = null;
        try {
            questionVO = questionService.getQuestionById(id);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(questionVO);
    }

    @GetMapping("/page/list")
    public RestResponse<Page<QuestionTableVO>> pageList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "keyword",required = false) String keyword,
            @RequestParam(value = "subject",required = false) String subject,
            @RequestParam(value = "questionType",required = false) Integer questionType){
        Page<QuestionTableVO> questionList = null;
        try {
            questionList = questionService.getPageList(pageNum, pageSize, keyword,subject,questionType);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        RestResponse<Page<QuestionTableVO>> restResponse = RestResponse.ok(questionList);
        return restResponse;
    }
}

