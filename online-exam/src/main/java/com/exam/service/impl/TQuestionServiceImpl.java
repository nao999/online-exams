package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.*;
import com.exam.entity.vo.QuestionTableVO;
import com.exam.entity.vo.QuestionVO;
import com.exam.entity.vo.StatisticsInfo;
import com.exam.mapper.*;
import com.exam.service.TQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.utils.QuestionType;
import com.exam.utils.Utils;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
@Service
public class TQuestionServiceImpl extends ServiceImpl<TQuestionMapper, TQuestion> implements TQuestionService {

    @Autowired
    private TQuestionMapper questionMapper;

    @Autowired
    private TChoiceItemMapper choiceItemMapper;

    @Autowired
    private KnowledgePointToQuestionMapper knowledgePointToQuestionMapper;

    @Autowired
    private StudentToQuestionMapper studentToQuestionMapper;

    @Autowired
    private TUserMapper userMapper;

    @Override
    public void saveQuestion(QuestionVO question) {
        // VO转为PO
        TQuestion tQuestion = new TQuestion();
        BeanUtils.copyProperties(question, tQuestion);

        String questionTypeName = QuestionType.getQuestionType(tQuestion.getQuestionType());
        tQuestion.setQuestionTypeName(questionTypeName);

        // 保存题目
        baseMapper.insert(tQuestion);
        Integer questionType = tQuestion.getQuestionType();
        if(questionType == 1 || questionType == 2 || questionType == 3){
            // 为单选题或多选题或判断题就保存选项
            List<TChoiceItem> items = question.getItems();
            for(TChoiceItem item : items){
                item.setQuestionId(tQuestion.getId());
                choiceItemMapper.insert(item);
            }
        }

        // 保存题目的知识点
        for(List<String> pointItem : question.getPoint()){
            for(String point : pointItem){
                KnowledgePointToQuestion pointToQuestion = new KnowledgePointToQuestion();
                pointToQuestion.setKnowledgePointId(point);
                pointToQuestion.setQuestionId(tQuestion.getId());
                knowledgePointToQuestionMapper.insert(pointToQuestion);
            }
        }

    }

    @Override
    public Page<QuestionTableVO> getPageList(Integer pageNum, Integer pageSize, String keyword, String subject, Integer questionType) {
        Page<QuestionTableVO> page = new Page<>(pageNum,pageSize);

//        questionMapper.selectQuestion();
        page.setRecords(questionMapper.selectQuestion(page,keyword,subject,questionType));
        return page;
    }

    @Override
    public QuestionVO getQuestionById(String id) {
        QuestionVO questionVO = questionMapper.selectQuestionById(id);
        List<TKnowledgePoint> points = questionMapper.selectPoints(id);
        if(points.size() == 1 && points.get(0) == null){
            // 如果没有知识点了就直接返回
            return questionVO;
        }
        List<List<String>> pointList = new ArrayList<>();
        List<String> pointListItem = new ArrayList<>();
        for(int i = 0;i < points.size(); i++){
            if((i + 1) == points.size() || points.get(i+1).getLevel() == 1 ){
                // 如果下一个为根节点加入总的集合，并重置知识点集合
                pointListItem.add(points.get(i).getId());
                pointList.add(pointListItem);
                pointListItem = new ArrayList<>();
            }else{
                pointListItem.add(points.get(i).getId());
            }

        }
        questionVO.setPoint(pointList);
        return questionVO;
    }

    @Override
    public void updateQuestion(QuestionVO questionVO) {
        TQuestion tQuestion = new TQuestion();
        BeanUtils.copyProperties(questionVO, tQuestion);
        String questionTypeName = QuestionType.getQuestionType(tQuestion.getQuestionType());
        tQuestion.setQuestionTypeName(questionTypeName);

        Integer questionType = tQuestion.getQuestionType();
        baseMapper.updateById(tQuestion);

        if(questionType == 1 || questionType == 2 || questionType == 3){
            // 为单选题或多选题或判断题就保存选项
            List<TChoiceItem> items = questionVO.getItems();
            for(TChoiceItem item : items){
                item.setQuestionId(tQuestion.getId());
                choiceItemMapper.updateById(item);
            }
        }

        // 更新知识点
        // 由于知识点数量不确定，所以直接删除再插入来操作
        QueryWrapper<KnowledgePointToQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("question_id", tQuestion.getId());
        knowledgePointToQuestionMapper.delete(wrapper);

        // 保存题目的知识点
        for(List<String> pointItem : questionVO.getPoint()){
            for(String point : pointItem){
                KnowledgePointToQuestion pointToQuestion = new KnowledgePointToQuestion();
                pointToQuestion.setKnowledgePointId(point);
                pointToQuestion.setQuestionId(tQuestion.getId());
                knowledgePointToQuestionMapper.insert(pointToQuestion);
            }
        }
    }

    @Override
    public void deleteQuestion(String id) {
        // 先删知识点和选项
        QueryWrapper<TChoiceItem> wrapperChoiceItem = new QueryWrapper<>();
        wrapperChoiceItem.eq("question_id", id);
        choiceItemMapper.delete(wrapperChoiceItem);

        QueryWrapper<KnowledgePointToQuestion> wrapperPointToQ = new QueryWrapper<>() ;
        wrapperPointToQ.eq("question_id", id);
        knowledgePointToQuestionMapper.delete(wrapperPointToQ);

        // 删除question
        questionMapper.deleteById(id);
    }

    @Override
    public List<QuestionVO> getExerciseQuestion(String subjectId,String pointId,Integer exerciseType) {
        // 查出做过的题目
        String userId = Utils.getCurrentUserId(userMapper);
//        List<String> questionList = studentToQuestionMapper.selectQuestionList(userId);
        // 先查单选题
        List<QuestionVO> questionVOs = questionMapper.selectQuestionForUser(subjectId,1,8,userId,pointId);
        // 查出多选题
        List<QuestionVO> multiQuestionVOs = questionMapper.selectQuestionForUser(subjectId,2,4,userId,pointId);
        questionVOs.addAll(multiQuestionVOs);

        if(exerciseType == 2){
            // 如果是试卷训练就查出简答题
            List<QuestionVO> shortAnswerQuestionVOs = questionMapper.selectQuestionForUser(subjectId,5,4,userId,pointId);
            questionVOs.addAll(shortAnswerQuestionVOs);
        }

        for(QuestionVO questionVO : questionVOs){
            List<TChoiceItem> items = choiceItemMapper.selectChoiceItem(questionVO.getId());
            questionVO.setItems(items);
        }
        return questionVOs;
    }




}
