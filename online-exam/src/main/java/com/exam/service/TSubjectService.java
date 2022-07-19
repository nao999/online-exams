package com.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TSubject;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-19
 */
public interface TSubjectService extends IService<TSubject> {

    Page<TSubject> getPageList(Integer pageNum, Integer pageSize, Integer level);
}
