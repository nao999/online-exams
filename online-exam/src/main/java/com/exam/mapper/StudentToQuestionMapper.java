package com.exam.mapper;

import com.exam.entity.StudentToQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface StudentToQuestionMapper extends BaseMapper<StudentToQuestion> {

    List<String> selectQuestionList(String userId);

    Integer selectDoneQuestionNumber(
            @Param("userId") String userId,
            @Param("pointId") String pointId);

    // 做题数量
    Integer selectQuestionCount(@Param("studentId") String studentId,@Param("subject") String subject);

    Integer selectRightCount(
            @Param("studentId") String studentId,
            @Param("subject") String subject);
}
