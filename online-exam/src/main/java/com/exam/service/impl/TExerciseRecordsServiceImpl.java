package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.entity.*;
import com.exam.entity.vo.*;
import com.exam.mapper.*;
import com.exam.service.TExerciseRecordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.service.TQuestionService;
import com.exam.utils.Utils;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-05
 */
@Service
public class TExerciseRecordsServiceImpl extends ServiceImpl<TExerciseRecordsMapper, TExerciseRecords> implements TExerciseRecordsService {

    @Autowired
    private TExerciseRecordsMapper exerciseRecordsMapper;

    @Autowired
    private ExerciseRecordToQuestionMapper exerciseRecordToQuestionMapper;

    @Autowired
    private StudentToQuestionMapper studentToQuestionMapper;

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private TQuestionMapper questionMapper;

    @Autowired
    private KnowledgePointToQuestionMapper knowledgePointToQuestionMapper;

    @Autowired
    private TQuestionService questionService;

    @Override
    public String saveExerciseRecords(ExerciseRecordVO exerciseRecordVO) {
        TExerciseRecords exerciseRecords = new TExerciseRecords();

        // 先保存训练记录
        BeanUtils.copyProperties(exerciseRecordVO, exerciseRecords);

        String studentId = Utils.getCurrentUserId(userMapper);

        exerciseRecords.setStudentId(studentId);

        exerciseRecordsMapper.insert(exerciseRecords);

        // 保存对应的题目关系表
        List<QuestionVO> questionList = exerciseRecordVO.getQuestionList();
        List<ChooseItem> chooseAnswers = exerciseRecordVO.getChooseAnswer();

        for (QuestionVO question : questionList) {
            ExerciseRecordToQuestion exerciseRecordToQuestion = new ExerciseRecordToQuestion();
            exerciseRecordToQuestion.setQuestionId(question.getId());
            exerciseRecordToQuestion.setExerciseRecordId(exerciseRecords.getId());
            ChooseItem chooseItem = null;

            // 寻找这一题选项
            for (ChooseItem chooseAnswer : chooseAnswers) {
                if (question.getId().equals(chooseAnswer.getQuestionId())) {
                    chooseItem = chooseAnswer;
                    break;
                }
            }
            if (chooseItem != null) {
                exerciseRecordToQuestion.setCorrectOrNot(chooseItem.getCorrectOrNot());
                String answer = chooseItem.getPrefix().toString().replaceAll(" ", "");
                answer = answer.substring(1, answer.length() - 1);
                exerciseRecordToQuestion.setChooseAnswer(answer);
            }
            exerciseRecordToQuestionMapper.insert(exerciseRecordToQuestion);

            // 记录用户做过的题目
            StudentToQuestion studentToQuestion = new StudentToQuestion();
            studentToQuestion.setQuestionId(question.getId());
            String userId = Utils.getCurrentUserId(userMapper);
            studentToQuestion.setStudentId(userId);
            studentToQuestionMapper.insert(studentToQuestion);
        }

        return exerciseRecords.getId();

    }

    @Override
    public ExerciseRecordVO getExerciseRecordsById(String id) {
        ExerciseRecordVO exerciseRecordVO = new ExerciseRecordVO();
        TExerciseRecords exerciseRecords = exerciseRecordsMapper.selectById(id);
        BeanUtils.copyProperties(exerciseRecords, exerciseRecordVO);

        QueryWrapper<ExerciseRecordToQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("exercise_record_id", exerciseRecords.getId());
        List<ExerciseRecordToQuestion> exerciseRecordToQuestionList = exerciseRecordToQuestionMapper.selectList(wrapper);

        List<QuestionVO> questionVOList = new ArrayList<>();
        List<ChooseItem> chooseItemList = new ArrayList<>();
        for (ExerciseRecordToQuestion exerciseRecordToQuestion : exerciseRecordToQuestionList) {
            QuestionVO questionVO = questionMapper.selectQuestionById(exerciseRecordToQuestion.getQuestionId());
            List<TKnowledgePoint> points = questionMapper.selectPoints(exerciseRecordToQuestion.getQuestionId());
            if (!(points.size() == 1 && points.get(0) == null)) {
                // 存在知识点
                List<String> pointList = new ArrayList<>();
                String pointListItem = "";
                for (int i = 0; i < points.size(); i++) {
                    if ((i + 1) == points.size() || points.get(i + 1).getLevel() == 1) {
                        // 如果下一个为根节点加入总的集合，并重置知识点集合
                        pointListItem = pointListItem + "/" + points.get(i).getKnowledgePointName();
                        // 把开头的/去掉
                        pointListItem = pointListItem.substring(1);
                        pointList.add(pointListItem);
                        pointListItem = "";
                    } else {
                        pointListItem = pointListItem + "/" + points.get(i).getKnowledgePointName();
                    }

                }

                questionVO.setPointList(pointList);
            }


            questionVOList.add(questionVO);
            String answer = exerciseRecordToQuestion.getChooseAnswer();
            List<String> chooseList = null;
            if (answer != null) {
                chooseList = Arrays.asList(answer.split(","));
            }
            ChooseItem chooseItem = new ChooseItem();
            chooseItem.setPrefix(chooseList);
            chooseItem.setCorrectOrNot(exerciseRecordToQuestion.getCorrectOrNot());
            chooseItem.setQuestionId(exerciseRecordToQuestion.getQuestionId());
            chooseItemList.add(chooseItem);
        }
        exerciseRecordVO.setQuestionList(questionVOList);
        exerciseRecordVO.setChooseAnswer(chooseItemList);
        return exerciseRecordVO;
    }

    @Override
    public List<ExerciseRecordVO> getExerciseList() {
        String studentId = Utils.getCurrentUserId(userMapper);
        List<ExerciseRecordVO> exerciseList = exerciseRecordsMapper.getExerciseList(studentId);
        return exerciseList;
    }

    @Override
    public StatisticsInfo getStatisticsInfo(String subject) {
        StatisticsInfo statisticsInfo = new StatisticsInfo();
        String studentId = Utils.getCurrentUserId(userMapper);
        List<String> timeList = exerciseRecordsMapper.selectTime(studentId, subject);
        // 计算平均时间
        String avgTime = avgTime(timeList);
        statisticsInfo.setAveTime(avgTime);
        Integer questionCount = studentToQuestionMapper.selectQuestionCount(studentId, subject);
        Integer rightCount = studentToQuestionMapper.selectRightCount(studentId, subject);
        statisticsInfo.setTotalQuestionNum(questionCount);
        statisticsInfo.setRightNum(rightCount);

        return statisticsInfo;
    }

    private String avgTime(List<String> timeList) {
        Integer sumTime = 0;
        if (timeList.size() == 0) {
            return sumTime + "";
        }
        for (String time : timeList) {
            String[] split = time.split(":");
            Integer hour = Integer.valueOf(split[0]) * 3600;
            Integer minute = Integer.valueOf(split[1]) * 60;
            Integer second = Integer.valueOf(split[2]);
            sumTime += hour + minute + second;
        }

        sumTime = sumTime / timeList.size();

        // 转为时分秒的格式
        int hour = sumTime / 3600;
        sumTime = sumTime % 3600;

        int minutes = sumTime / 60;
        sumTime = sumTime % 60;

        int second = sumTime;

        String avgTime;
        avgTime = hour >= 10 ? (hour + "") : ("0" + hour);
        avgTime = avgTime + ":" + (minutes >= 10 ? (minutes + "") : ("0" + minutes));
        avgTime = avgTime + ":" + (second >= 10 ? (second + "") : ("0" + second));

        return avgTime;
    }
}
