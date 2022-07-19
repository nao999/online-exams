package com.exam.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TChoiceItem;
import com.exam.entity.TKnowledgePoint;
import com.exam.entity.TQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.vo.QuestionTableVO;
import com.exam.entity.vo.QuestionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface TQuestionMapper extends BaseMapper<TQuestion> {

    List<QuestionTableVO> selectQuestion(
            @Param("page") Page<QuestionTableVO> page,
            @Param("keyword") String keyword,
            @Param("subject") String subject,
            @Param("questionType") Integer questionType);

    QuestionVO selectQuestionById(String id);

    List<TKnowledgePoint> selectPoints(String id);

    List<QuestionVO> selectQuestionForUser(
            @Param("id") String id,
            @Param("questionType") Integer questionType,
            @Param("questionNum") Integer questionNum,
            @Param("userId") String userId,
            @Param("pointId") String pointId);
}
