package com.exam.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TPaper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.entity.vo.PaperVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-28
 */
public interface TPaperMapper extends BaseMapper<TPaper> {

    List<PaperVO> selectPaper(
            @Param("page") Page<PaperVO> page,
            @Param("keyword") String keyword,
            @Param("subject") String subject);

    PaperVO selectPaperById(String id);
}
