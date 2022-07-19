package com.exam.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TErrorList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.vo.ErrorListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-08-31
 */
public interface TErrorListMapper extends BaseMapper<TErrorList> {

    List<ErrorListVO> selectErrorList(
            @Param("page") Page<ErrorListVO> page,
            @Param("keyword") String keyword,
            @Param("studentId") String studentId,
            @Param("subject") String subject);

}
