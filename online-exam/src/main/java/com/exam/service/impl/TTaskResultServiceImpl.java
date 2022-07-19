package com.exam.service.impl;

import com.exam.entity.TTaskResult;
import com.exam.entity.TTaskResultAnswer;
import com.exam.entity.vo.QuestionAnswer;
import com.exam.entity.vo.TaskResultVO;
import com.exam.mapper.TTaskResultAnswerMapper;
import com.exam.mapper.TTaskResultMapper;
import com.exam.mapper.TUserMapper;
import com.exam.service.TTaskResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.utils.Utils;
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
 * @since 2021-08-28
 */
@Service
public class TTaskResultServiceImpl extends ServiceImpl<TTaskResultMapper, TTaskResult> implements TTaskResultService {

    @Autowired
    private TTaskResultMapper taskResultMapper;

    @Autowired
    private TTaskResultAnswerMapper taskResultAnswerMapper;

    @Autowired
    private TUserMapper userMapper;

    @Override
    public void saveTaskResult(TaskResultVO taskResultVO) {

        TTaskResult taskResult = new TTaskResult();

        String userId = Utils.getCurrentUserId(userMapper);
        taskResult.setStudentId(userId);
        taskResult.setTaskId(taskResultVO.getTaskId());
        // 保存任务结果信息
        taskResultMapper.insert(taskResult);

        // 循环保存到taskResultAnswer中
        for(QuestionAnswer questionAnswer : taskResultVO.getAnswer()){
            TTaskResultAnswer taskResultAnswerItem = new TTaskResultAnswer();
            taskResultAnswerItem.setQuestionId(questionAnswer.getQuestionId());
            taskResultAnswerItem.setTaskResultId(taskResult.getId());

            String answer = "";
            for(String str : questionAnswer.getAnswer()){
                answer = answer + str + ",";
            }
            answer = answer.substring(0, answer.length()-1);
            taskResultAnswerItem.setAnswer(answer);
            taskResultAnswerMapper.insert(taskResultAnswerItem);
        }

    }
}
