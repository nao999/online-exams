package com.exam.service;

import com.exam.entity.TTaskResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.vo.TaskResultVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-28
 */
public interface TTaskResultService extends IService<TTaskResult> {

    void saveTaskResult(TaskResultVO taskResultVO);

}
