package com.exam.mapper;

import com.exam.entity.TSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-19
 */
public interface TSubjectMapper extends BaseMapper<TSubject> {

    List<TSubject> selectSubjectList(Integer userLevel);
}
