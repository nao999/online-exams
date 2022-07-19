package com.exam.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.vo.TaskVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-08-10
 */
public interface TTaskMapper extends BaseMapper<TTask> {

    List<TaskVO> selectTasks(
            @Param("page") Page<TaskVO> page,
            @Param("keyword") String keyword,
            @Param("level") String level);


    List<TaskVO> selectStuTasks(
            @Param("page") Page<TaskVO> page,
            @Param("keyword") String keyword,
            @Param("level") String level,
            @Param("subjectId") String subjectId);

    List<TaskVO> getUnfinishedTask(
            @Param("level") Integer level);
}
