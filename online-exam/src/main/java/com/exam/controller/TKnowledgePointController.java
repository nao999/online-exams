package com.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.KnowledgePointToQuestion;
import com.exam.entity.TKnowledgePoint;
import com.exam.entity.TUser;
import com.exam.entity.vo.PointEditVO;
import com.exam.entity.vo.PointVO;
import com.exam.entity.vo.UserVO;
import com.exam.exception.OnlineExamException;
import com.exam.service.KnowledgePointToQuestionService;
import com.exam.service.TKnowledgePointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-21
 */
@RestController
@RequestMapping("/point")
@CrossOrigin
public class TKnowledgePointController {

    @Autowired
    private TKnowledgePointService pointService;

    @Autowired
    private KnowledgePointToQuestionService knowledgePointToQuestionService;

    Logger logger = LoggerFactory.getLogger(TKnowledgePointController.class);


    @DeleteMapping("/deletePoint")
    public RestResponse<String> deletePoint(@RequestParam("id") String id){
        try {
            pointService.removeById(id);
            // 还要把题目对应的知识点删除
            QueryWrapper<KnowledgePointToQuestion> wrapper = new QueryWrapper<>();
            wrapper.eq("knowledge_point_id", id);
            knowledgePointToQuestionService.remove(wrapper);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @PostMapping("/addPoint")
    public RestResponse<TKnowledgePoint> addPoint(@RequestBody TKnowledgePoint point){
        try {
            pointService.save(point);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(point);
    }

    @GetMapping("/getPointById/{id}")
    public RestResponse<TKnowledgePoint> getPointById(@PathVariable("id") String id){
        TKnowledgePoint point = null;
        try {
            point = pointService.getById(id);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok(point);
    }

    // 得到所有的知识点信息
    @GetMapping("/getAllPoint")
    public RestResponse<List<PointEditVO>> getAllPoint(@RequestParam("subjectId") String subjectId){
        List<PointEditVO> pointEditVOList = null;
        try {
            pointEditVOList = pointService.getAllPoint(subjectId);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }


        return RestResponse.ok(pointEditVOList);
    }

    // 显示信息
    @GetMapping("/page/list")
    public RestResponse<Page<PointVO>> pageList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("level") Integer level){
        Page<PointVO> pointList = null;
        try {
            pointList = pointService.getPageList(pageNum, pageSize, level);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        RestResponse<Page<PointVO>> restResponse = RestResponse.ok(pointList);
        return restResponse;
    }

}

