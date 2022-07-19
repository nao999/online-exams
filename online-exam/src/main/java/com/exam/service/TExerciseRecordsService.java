package com.exam.service;

import com.exam.entity.TExerciseRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.vo.ExerciseRecordVO;
import com.exam.entity.vo.StatisticsInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-05
 */
public interface TExerciseRecordsService extends IService<TExerciseRecords> {

    String saveExerciseRecords(ExerciseRecordVO exerciseRecordVO);

    ExerciseRecordVO getExerciseRecordsById(String id);

    List<ExerciseRecordVO> getExerciseList();

    StatisticsInfo getStatisticsInfo(String subject);
}
