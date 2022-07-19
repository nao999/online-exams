package com.exam.service.impl;

import com.exam.entity.TPredictScore;
import com.exam.entity.TSubject;
import com.exam.entity.TUser;
import com.exam.entity.vo.PredictScoreVO;
import com.exam.mapper.StudentToQuestionMapper;
import com.exam.mapper.TPredictScoreMapper;
import com.exam.mapper.TSubjectMapper;
import com.exam.mapper.TUserMapper;
import com.exam.service.TPredictScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-09-16
 */
@Service
public class TPredictScoreServiceImpl extends ServiceImpl<TPredictScoreMapper, TPredictScore> implements TPredictScoreService {

    @Autowired
    private TPredictScoreMapper predictScoreMapper;

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private TSubjectMapper subjectMapper;

    @Autowired
    private StudentToQuestionMapper studentToQuestionMapper;


    @Override
    public void savePredictScore() {
        // 查出当前学生的年级
        TUser student = Utils.getCurrentUser(userMapper);
        // 查出所有的学科
        List<TSubject> subjectList = subjectMapper.selectSubjectList(student.getUserLevel());
        for(TSubject subject : subjectList){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(new Date());
             
            List<TPredictScore> predictScore = predictScoreMapper.selectPredictScore(student.getId(),subject.getId(),date);
            // 判断是否存在
            if(predictScore == null || predictScore.size() == 0){
                TPredictScore tPredictScore = new TPredictScore();
                // 查出当前做对的题和总题数
                Integer questionCount = studentToQuestionMapper.selectQuestionCount(student.getId(), subject.getId());
                Integer rightCount = studentToQuestionMapper.selectRightCount(student.getId(), subject.getId());
                if(questionCount != 0){
                    tPredictScore.setScore(Math.round((float)rightCount / questionCount * 100));
                }else{
                    tPredictScore.setScore(0);
                }
                tPredictScore.setStudentId(student.getId());
                tPredictScore.setSubjectId(subject.getId());
                tPredictScore.setTime(date);
                predictScoreMapper.insert(tPredictScore);
            }
        }
    }

    @Override
    public List<PredictScoreVO> getPredictScore(String subject) {
        String userId = Utils.getCurrentUserId(userMapper);
        List<PredictScoreVO> predictScoreVOList = predictScoreMapper.getPredictScore(userId,subject);
        Collections.reverse(predictScoreVOList);
        return predictScoreVOList;
    }
}
