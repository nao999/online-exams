package com.exam.mapper;

import com.exam.entity.TChoiceItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-23
 */
public interface TChoiceItemMapper extends BaseMapper<TChoiceItem> {
    List<TChoiceItem> selectChoiceItem(String id);
}
