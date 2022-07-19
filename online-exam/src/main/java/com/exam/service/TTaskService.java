package com.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TTask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.vo.TaskVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-10
 */
public interface TTaskService extends IService<TTask> {

    Page<TaskVO> getPageList(Integer pageNum, Integer pageSize, String keyword, String level);


    Page<TaskVO> getStuPageList(Integer pageNum, Integer pageSize, String keyword,String subjectId);

    List<TaskVO> getUnfinishedTask();

}
