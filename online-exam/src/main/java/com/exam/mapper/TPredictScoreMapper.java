package com.exam.mapper;

import com.exam.entity.TPredictScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.vo.PredictScoreVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-09-16
 */
public interface TPredictScoreMapper extends BaseMapper<TPredictScore> {


    List<TPredictScore> selectPredictScore(
            @Param("studentId") String studentId,
            @Param("subjectId") String subjectId,
            @Param("date") String date);

    List<PredictScoreVO> getPredictScore(
            @Param("userId") String userId,
            @Param("subject") String subject);
}
