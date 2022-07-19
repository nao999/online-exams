package com.exam.mapper;

import com.exam.entity.TExerciseRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.vo.ExerciseRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-08-05
 */
public interface TExerciseRecordsMapper extends BaseMapper<TExerciseRecords> {

    List<ExerciseRecordVO> getExerciseList(@Param("studentId") String studentId);

    List<String> selectTime(
            @Param("studentId") String studentId,
            @Param("subject") String subject);

}
