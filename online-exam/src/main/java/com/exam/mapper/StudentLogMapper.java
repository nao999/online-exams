package com.exam.mapper;

import com.exam.entity.StudentLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-10-04
 */
public interface StudentLogMapper extends BaseMapper<StudentLog> {

    List<StudentLog> getLogInfo(@Param("studentId") String studentId);

}
