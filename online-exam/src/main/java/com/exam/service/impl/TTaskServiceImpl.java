package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TTask;
import com.exam.entity.TTaskResult;
import com.exam.entity.TUser;
import com.exam.entity.vo.TaskVO;
import com.exam.mapper.TTaskMapper;
import com.exam.mapper.TTaskResultMapper;
import com.exam.mapper.TUserMapper;
import com.exam.service.TTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-10
 */
@Service
public class TTaskServiceImpl extends ServiceImpl<TTaskMapper, TTask> implements TTaskService {

    @Autowired
    private TTaskMapper taskMapper;

    @Autowired
    private TTaskResultMapper taskResultMapper;

    @Autowired
    private TUserMapper userMapper;

    @Override
    public Page<TaskVO> getPageList(Integer pageNum, Integer pageSize, String keyword, String level) {
        Page<TaskVO> page = new Page<>(pageNum,pageSize);
        page.setRecords(taskMapper.selectTasks(page,keyword,level));
        return page;
    }

    @Override
    public Page<TaskVO> getStuPageList(Integer pageNum, Integer pageSize, String keyword,String subjectId) {
        Page<TaskVO> page = new Page<>(pageNum,pageSize);
        TUser user = Utils.getCurrentUser(userMapper);
        String level = user.getUserLevel() + "";
        String userId = user.getId();
        page.setRecords(taskMapper.selectStuTasks(page,keyword,level,subjectId));
        List<TaskVO> records = page.getRecords();
        // 查询对于当前学生该任务的状态
        for(TaskVO taskVO : records){
            QueryWrapper<TTaskResult> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("task_id", taskVO.getId());
            queryWrapper.eq("student_id", userId);
            TTaskResult taskResult = taskResultMapper.selectOne(queryWrapper);
            if(taskResult == null){
                taskVO.setStatus(0);
            }else{
                taskVO.setStatus(taskResult.getStatus());
            }
        }
        return page;
    }

    @Override
    public List<TaskVO> getUnfinishedTask() {
        TUser user = Utils.getCurrentUser(userMapper);
        return taskMapper.getUnfinishedTask(user.getUserLevel());
    }


}
