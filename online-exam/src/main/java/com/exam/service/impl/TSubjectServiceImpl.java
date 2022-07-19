package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TSubject;
import com.exam.mapper.TSubjectMapper;
import com.exam.service.TSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-19
 */
@Service
public class TSubjectServiceImpl extends ServiceImpl<TSubjectMapper, TSubject> implements TSubjectService {

    @Override
    public Page<TSubject> getPageList(Integer pageNum, Integer pageSize, Integer level) {
        QueryWrapper<TSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("level", level);
        Page<TSubject> page = new Page<>(pageNum,pageSize);
        baseMapper.selectPage(page, wrapper);
        return page;
    }
}
