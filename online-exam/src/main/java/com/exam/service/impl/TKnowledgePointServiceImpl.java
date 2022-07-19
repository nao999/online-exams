package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TKnowledgePoint;
import com.exam.entity.TSubject;
import com.exam.entity.TUser;
import com.exam.entity.vo.PointEditVO;
import com.exam.entity.vo.PointVO;
import com.exam.entity.vo.UserVO;
import com.exam.mapper.KnowledgePointToQuestionMapper;
import com.exam.mapper.StudentToQuestionMapper;
import com.exam.mapper.TKnowledgePointMapper;
import com.exam.mapper.TUserMapper;
import com.exam.service.TKnowledgePointService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.service.TSubjectService;
import com.exam.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.resources.cldr.ksh.TimeZoneNames_ksh;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-21
 */
@Service
public class TKnowledgePointServiceImpl extends ServiceImpl<TKnowledgePointMapper, TKnowledgePoint> implements TKnowledgePointService {

    @Autowired
    private TSubjectService subjectService;

    @Autowired
    private KnowledgePointToQuestionMapper knowledgePointToQuestionMapper;

    @Autowired
    private StudentToQuestionMapper studentToQuestionMapper;

    @Autowired
    private TUserMapper userMapper;

    @Override
    public Page<PointVO> getPageList(Integer pageNum, Integer pageSize, Integer level) {


        Page<TSubject> page = subjectService.getPageList(pageNum, pageSize, level);

        Page<PointVO> pointVOPage = new Page<>(pageNum,pageSize);

        // 转换为VO
        BeanUtils.copyProperties(page, pointVOPage);
        pointVOPage.setRecords(new ArrayList<>());

        List<TSubject> tSubjects = page.getRecords();

        for(TSubject tSubject : tSubjects){
            PointVO pointVO = new PointVO();
            BeanUtils.copyProperties(tSubject, pointVO);
            String subjectId = tSubject.getId();
            QueryWrapper<TKnowledgePoint> pointQueryWrapper = new QueryWrapper<>();
            pointQueryWrapper.eq("knowledge_point_subjectid", subjectId);
            Integer count = baseMapper.selectCount(pointQueryWrapper);
            pointVO.setPointNumber(count);
            pointVOPage.getRecords().add(pointVO);
        }


        return pointVOPage;
    }

    @Override
    public List<PointEditVO> getAllPoint(String subjectId) {
        // 拿到当前用户id
        String userId = Utils.getCurrentUserId(userMapper);

        QueryWrapper<TKnowledgePoint> wrapper = new QueryWrapper<>();
        wrapper.eq("knowledge_point_subjectid", subjectId);
        // 找到属于当前学科所有的根节点
        wrapper.eq("parent_id",0);
        List<TKnowledgePoint> rootPoints = baseMapper.selectList(wrapper);
        List<PointEditVO> pointEditVOList = new ArrayList<>();
        // 遍历查找所有子节点
        for(TKnowledgePoint point : rootPoints) {
            PointEditVO pointEditVO = new PointEditVO();
            pointEditVO.setId(point.getId());
            pointEditVO.setValue(point.getId());
            pointEditVO.setLabel(point.getKnowledgePointName());
            pointEditVO.setLevel(point.getLevel());

            QueryWrapper<TKnowledgePoint> secondWrapper = new QueryWrapper<>();
            secondWrapper.eq("parent_id", point.getId());
            secondWrapper.eq("level", 2);
            List<TKnowledgePoint> secondPointList = baseMapper.selectList(secondWrapper);
            List<PointEditVO> secondPointEditList = new ArrayList<>();
            if(secondPointList != null && secondPointList.size() != 0){
                for (TKnowledgePoint secondPoint : secondPointList){
                    PointEditVO secondpointEditVO = new PointEditVO();
                    secondpointEditVO.setId(secondPoint.getId());
                    secondpointEditVO.setValue(secondPoint.getId());
                    secondpointEditVO.setLabel(secondPoint.getKnowledgePointName());
                    secondpointEditVO.setLevel(secondPoint.getLevel());

                    QueryWrapper<TKnowledgePoint> thirdWrapper = new QueryWrapper<>();
                    thirdWrapper.eq("parent_id", secondPoint.getId());
                    thirdWrapper.eq("level", 3);
                    List<TKnowledgePoint> thirdPointList = baseMapper.selectList(thirdWrapper);
                    List<PointEditVO> thirdPointEditList = new ArrayList<>();
                    if(thirdPointList != null && thirdPointList.size() != 0){
                        for(TKnowledgePoint thirdPoint : thirdPointList){
                            PointEditVO thirdpointEditVO = new PointEditVO();
                            thirdpointEditVO.setId(thirdPoint.getId());
                            thirdpointEditVO.setValue(thirdPoint.getId());
                            thirdpointEditVO.setLabel(thirdPoint.getKnowledgePointName());
                            thirdpointEditVO.setLevel(thirdPoint.getLevel());
                            // 没有子元素要设置为null不然前端会显示错误
                            thirdpointEditVO.setChildren(null);
                            // 查询知识点对应的题目数量
                            thirdpointEditVO.setQuestionNumber(knowledgePointToQuestionMapper.selectQuestionNumber(thirdpointEditVO.getId()));
                            // 查询已经做过的题目的数量
                            thirdpointEditVO.setDoneQuestionNumber(studentToQuestionMapper.selectDoneQuestionNumber(userId,thirdpointEditVO.getId()));

                            thirdPointEditList.add(thirdpointEditVO);
                        }

                        secondpointEditVO.setChildren(thirdPointEditList);
                    }else{
                        // 没有子元素要设置为null不然前端会显示错误
                        secondpointEditVO.setChildren(null);
                    }
                    // 查询知识点对应的题目数量
                    secondpointEditVO.setQuestionNumber(knowledgePointToQuestionMapper.selectQuestionNumber(secondpointEditVO.getId()));
                    // 查询已经做过的题目的数量
                    secondpointEditVO.setDoneQuestionNumber(studentToQuestionMapper.selectDoneQuestionNumber(userId,secondpointEditVO.getId()));



                    secondPointEditList.add(secondpointEditVO);
                }
                pointEditVO.setChildren(secondPointEditList);
            }else{
                // 没有子元素要设置为null不然前端会显示错误
                pointEditVO.setChildren(null);
            }
            // 查询知识点对应的题目数量
            pointEditVO.setQuestionNumber(knowledgePointToQuestionMapper.selectQuestionNumber(pointEditVO.getId()));
            // 查询已经做过的题目的数量
            pointEditVO.setDoneQuestionNumber(studentToQuestionMapper.selectDoneQuestionNumber(userId,pointEditVO.getId()));



            pointEditVOList.add(pointEditVO);
        }
        return pointEditVOList;
    }
}
