package com.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TPaper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.vo.PaperVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-28
 */
public interface TPaperService extends IService<TPaper> {

    void addPaper(PaperVO paperVO);

    Page<PaperVO> getPageList(Integer pageNum, Integer pageSize, String keyword, String subject);

    PaperVO getPaperById(String id);

    void editPaper(PaperVO paperVO);

    void deletePaper(String id);
}
