package com.exam.service;

import com.exam.entity.StudentLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.vo.StudentLogVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-04
 */
public interface StudentLogService extends IService<StudentLog> {

    void logLogin();

    void logLogout();

    void logExercise(Integer exerciseType);

    List<StudentLogVO> getLogInfo();

}
