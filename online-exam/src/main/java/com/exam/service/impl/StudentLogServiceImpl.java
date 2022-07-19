package com.exam.service.impl;

import com.exam.entity.StudentLog;
import com.exam.entity.TUser;
import com.exam.entity.vo.StudentLogVO;
import com.exam.mapper.StudentLogMapper;
import com.exam.mapper.TUserMapper;
import com.exam.service.StudentLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2021-10-04
 */
@Service
public class StudentLogServiceImpl extends ServiceImpl<StudentLogMapper, StudentLog> implements StudentLogService {

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private StudentLogMapper studentLogMapper;

    @Override
    public void logLogin() {
        String studentId = Utils.getCurrentUserId(userMapper);
        StudentLog studentLog = new StudentLog();
        studentLog.setStudentId(studentId);
        studentLog.setOperationType(1);
        studentLogMapper.insert(studentLog);
    }

    @Override
    public void logLogout() {
        String studentId = Utils.getCurrentUserId(userMapper);
        StudentLog studentLog = new StudentLog();
        studentLog.setStudentId(studentId);
        studentLog.setOperationType(2);
        studentLogMapper.insert(studentLog);
    }

    @Override
    public void logExercise(Integer exerciseType) {
        String studentId = Utils.getCurrentUserId(userMapper);
        StudentLog studentLog = new StudentLog();
        studentLog.setStudentId(studentId);
        if(exerciseType == 0){
            studentLog.setOperationType(3);
        }else if(exerciseType == 1){
            studentLog.setOperationType(4);
        }else if(exerciseType == 2){
            studentLog.setOperationType(5);
        }
        studentLogMapper.insert(studentLog);
    }

    @Override
    public List<StudentLogVO> getLogInfo() {
        String studentId = Utils.getCurrentUserId(userMapper);
        TUser user = Utils.getCurrentUser(userMapper);
        List<StudentLog> studentLogList = studentLogMapper.getLogInfo(studentId);
        List<StudentLogVO> studentLogVOList = new ArrayList<>();
        for (StudentLog studentLog : studentLogList){
            StudentLogVO studentLogVO = new StudentLogVO();
            BeanUtils.copyProperties(studentLog, studentLogVO);
            String operation = Utils.logTypeTrans(studentLog.getOperationType());
            operation = user.getUserName() + operation;
            studentLogVO.setOperationType(operation);
            studentLogVO.setStudentName(user.getUserName());
            studentLogVOList.add(studentLogVO);
        }
        return studentLogVOList;
    }
}
