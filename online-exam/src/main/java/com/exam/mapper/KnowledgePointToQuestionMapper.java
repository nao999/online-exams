package com.exam.mapper;

import com.exam.entity.KnowledgePointToQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.vo.PointVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface KnowledgePointToQuestionMapper extends BaseMapper<KnowledgePointToQuestion> {

    List<PointVO> selectPointsByQuestionId(String id);

    Integer selectQuestionNumber(String id);

}
