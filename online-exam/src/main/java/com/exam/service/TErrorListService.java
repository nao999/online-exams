package com.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TErrorList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.vo.ErrorListVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-31
 */
public interface TErrorListService extends IService<TErrorList> {

    void saveErrorQuestion(ErrorListVO errorListVO);

    Page<ErrorListVO> pageList(Integer pageNum, Integer pageSize, String keyword, String subject);

}
