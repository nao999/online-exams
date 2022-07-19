package com.exam.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TKnowledgePoint;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.vo.PointEditVO;
import com.exam.entity.vo.PointVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-21
 */
public interface TKnowledgePointService extends IService<TKnowledgePoint> {

    Page<PointVO> getPageList(Integer pageNum, Integer pageSize, Integer level);

    List<PointEditVO> getAllPoint(String subjectId);
}
