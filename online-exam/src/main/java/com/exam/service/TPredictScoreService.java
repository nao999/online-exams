package com.exam.service;

import com.exam.entity.TPredictScore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.vo.PredictScoreVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-09-16
 */
public interface TPredictScoreService extends IService<TPredictScore> {

    void savePredictScore();

    List<PredictScoreVO> getPredictScore(String subject);
}
