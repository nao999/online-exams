package com.exam.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.entity.TPaperItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.vo.PaperItem;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-28
 */
public interface TPaperItemMapper extends BaseMapper<TPaperItem> {

    public List<PaperItem> selectPaperItem(String id);

}
