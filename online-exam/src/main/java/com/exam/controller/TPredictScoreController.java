package com.exam.controller;


import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.TPredictScore;
import com.exam.entity.vo.PredictScoreVO;
import com.exam.service.TPredictScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-16
 */
@RestController
@RequestMapping("/predict-score")
@CrossOrigin
public class TPredictScoreController {

    @Autowired
    private TPredictScoreService predictScoreService;

    @GetMapping("/getPredictScore")
    public RestResponse<List<PredictScoreVO>> getPredictScore(@RequestParam("subject") String subject){

        List<PredictScoreVO> predictScoreVOList = null;
        try {
            predictScoreVOList = predictScoreService.getPredictScore(subject);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok(predictScoreVOList);
    }

    @PostMapping("/savePredictScore")
    public RestResponse<String> savePredictScore(){
        try {
            predictScoreService.savePredictScore();
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }
}

