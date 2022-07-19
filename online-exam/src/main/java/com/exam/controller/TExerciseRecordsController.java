package com.exam.controller;


import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.ExerciseRecordToQuestion;
import com.exam.entity.vo.ExerciseRecordVO;
import com.exam.entity.vo.StatisticsInfo;
import com.exam.service.StudentLogService;
import com.exam.service.TExerciseRecordsService;
import lombok.AllArgsConstructor;
import org.apache.tools.ant.types.resources.Restrict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-05
 */
@RestController
@RequestMapping("/exercise-records")
@CrossOrigin
public class TExerciseRecordsController {

    @Autowired
    private TExerciseRecordsService exerciseRecordsService;

    @Autowired
    private StudentLogService studentLogService;

    // 获得学生做题的统计信息，包括做的总题数、正确题数、平均用时等
    @GetMapping("/getStatisticsInfo")
    public RestResponse<StatisticsInfo> getStatisticsInfo(@RequestParam("subject") String subject){
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        try {
            statisticsInfo = exerciseRecordsService.getStatisticsInfo(subject);

        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok(statisticsInfo);
    }

    @PostMapping("/saveExerciseRecords")
    public RestResponse<String> saveExerciseRecords(@RequestBody ExerciseRecordVO exerciseRecordVO){
        String exerciseRecordsId = "";
        try {
            exerciseRecordsId = exerciseRecordsService.saveExerciseRecords(exerciseRecordVO);
            // 记录训练信息
            studentLogService.logExercise(exerciseRecordVO.getExerciseType());
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(exerciseRecordsId);

    }

    @GetMapping("/getExerciseRecordsById/{id}")
    public RestResponse<ExerciseRecordVO> getExerciseRecordsById(@PathVariable("id") String id){
        ExerciseRecordVO exerciseRecordVO = null;
        try {
            exerciseRecordVO = exerciseRecordsService.getExerciseRecordsById(id);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(exerciseRecordVO);
    }

    @GetMapping("/getExerciseList")
    public RestResponse<List<ExerciseRecordVO>> getExerciseList(){

        List<ExerciseRecordVO> exerciseList = null;
        try {
            exerciseList = exerciseRecordsService.getExerciseList();
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok(exerciseList);
    }

}

