package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.SystemCode;
import com.exam.entity.TErrorList;
import com.exam.entity.TKnowledgePoint;
import com.exam.entity.TSubject;
import com.exam.entity.vo.ErrorListVO;
import com.exam.entity.vo.QuestionVO;
import com.exam.exception.OnlineExamException;
import com.exam.mapper.TErrorListMapper;
import com.exam.mapper.TQuestionMapper;
import com.exam.mapper.TSubjectMapper;
import com.exam.mapper.TUserMapper;
import com.exam.service.TErrorListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.service.TQuestionService;
import com.exam.utils.QuestionType;
import com.exam.utils.Utils;
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
 * @since 2021-08-31
 */
@Service
public class TErrorListServiceImpl extends ServiceImpl<TErrorListMapper, TErrorList> implements TErrorListService {

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private TErrorListMapper errorListMapper;

    @Autowired
    private TQuestionService questionService;

    @Autowired
    private TSubjectMapper subjectMapper;

    @Autowired
    private TQuestionMapper questionMapper;

    @Override
    public void saveErrorQuestion(ErrorListVO errorListVO) {
        // 判断题目是否已经存在
        QueryWrapper<TErrorList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question_id", errorListVO.getQuestionId());
        List<TErrorList> lists = errorListMapper.selectList(queryWrapper);
        if(lists != null && lists.size() != 0){
            throw new OnlineExamException(SystemCode.QUESTION_EXIST.getCode(), SystemCode.QUESTION_EXIST.getMessage());
        }

        String userId = Utils.getCurrentUserId(userMapper);
        TErrorList errorList = new TErrorList();
        BeanUtils.copyProperties(errorListVO, errorList);
        errorList.setStudentId(userId);
        errorListMapper.insert(errorList);
    }

    @Override
    public Page<ErrorListVO> pageList(Integer pageNum, Integer pageSize, String keyword, String subject) {
        Page<ErrorListVO> page = new Page<>(pageNum,pageSize);
        String studentId = Utils.getCurrentUserId(userMapper);
        page.setRecords(errorListMapper.selectErrorList(page,keyword,studentId,subject));
        List<ErrorListVO> records = page.getRecords();
        // 将question信息保存进去
        for(ErrorListVO errorList : records){
            QuestionVO question = questionService.getQuestionById(errorList.getQuestionId());
            String levelName = Utils.changeLevelName(question.getLevel());
            question.setLevelName(levelName);
            String questionType = QuestionType.getQuestionType(question.getQuestionType());
            question.setQuestionTypeName(questionType);
            // 根据subjectId查学科名字
            TSubject tSubject = subjectMapper.selectById(question.getSubject());
            question.setSubject(tSubject.getName());

            // 保存知识点
            List<TKnowledgePoint> points = questionMapper.selectPoints(errorList.getQuestionId());
            if(!(points.size() == 1 && points.get(0) == null)){
                // 存在知识点
                List<String> pointList = new ArrayList<>();
                String pointListItem = "";
                for(int i = 0;i < points.size(); i++){
                    if((i + 1) == points.size() || points.get(i+1).getLevel() == 1 ){
                        // 如果下一个为根节点加入总的集合，并重置知识点集合
                        pointListItem = pointListItem + "/" + points.get(i).getKnowledgePointName();
                        // 把开头的/去掉
                        pointListItem = pointListItem.substring(1);
                        pointList.add(pointListItem);
                        pointListItem = "";
                    }else{
                        pointListItem = pointListItem + "/" + points.get(i).getKnowledgePointName();
                    }

                }

                question.setPointList(pointList);
            }

            errorList.setQuestionContent(question);

        }
        return page;
    }
}
