package com.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.vo.QuestionTableVO;
import com.exam.entity.vo.QuestionVO;
import com.exam.entity.vo.StatisticsInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface TQuestionService extends IService<TQuestion> {

    void saveQuestion(QuestionVO tQuestion);

    Page<QuestionTableVO> getPageList(Integer pageNum, Integer pageSize, String keyword, String subject, Integer questionType);

    QuestionVO getQuestionById(String id);


    void updateQuestion(QuestionVO questionVO);

    void deleteQuestion(String id);

    // 学生训练时得到题目
    List<QuestionVO> getExerciseQuestion(String subjectId,String pointId,Integer exerciseType);

}
