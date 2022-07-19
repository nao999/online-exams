package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.PaperItemToQuestion;
import com.exam.entity.TPaper;
import com.exam.entity.TPaperItem;
import com.exam.entity.vo.PaperItem;
import com.exam.entity.vo.PaperVO;
import com.exam.entity.vo.QuestionVO;
import com.exam.mapper.PaperItemToQuestionMapper;
import com.exam.mapper.TPaperItemMapper;
import com.exam.mapper.TPaperMapper;
import com.exam.service.TPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.service.TQuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-28
 */
@Service
public class TPaperServiceImpl extends ServiceImpl<TPaperMapper, TPaper> implements TPaperService {

    @Autowired
    private TPaperMapper paperMapper;

    @Autowired
    private TPaperItemMapper paperItemMapper;



    @Autowired
    private PaperItemToQuestionMapper paperItemToQuestionMapper;

    @Autowired
    private TQuestionService questionService;

    @Override
    public void addPaper(PaperVO paperVO) {
        TPaper tPaper = new TPaper();
        BeanUtils.copyProperties(paperVO, tPaper);
        // 保存试卷信息
        paperMapper.insert(tPaper);

        // 保存试卷里的题型分组
        for(PaperItem paperItem : paperVO.getQuestionItems()){
            TPaperItem tPaperItem = new TPaperItem();
            BeanUtils.copyProperties(paperItem, tPaperItem);
            tPaperItem.setTitle(paperItem.getQuestionTitle());
            tPaperItem.setPaperId(tPaper.getId());
            paperItemMapper.insert(tPaperItem);
            // 保存题型分组-题目关系表
            for(QuestionVO questionVO : paperItem.getQuestionContent()){
                PaperItemToQuestion paperItemToQuestion = new PaperItemToQuestion();
                paperItemToQuestion.setPaperItemId(tPaperItem.getId());
                paperItemToQuestion.setQuestionId(questionVO.getId());
                paperItemToQuestionMapper.insert(paperItemToQuestion);
            }
        }


    }

    @Override
    public Page<PaperVO> getPageList(Integer pageNum, Integer pageSize, String keyword, String subject) {
        Page<PaperVO> page = new Page<>(pageNum,pageSize);
        page.setRecords(paperMapper.selectPaper(page,keyword,subject));

        return page;
    }

    @Override
    public PaperVO getPaperById(String id) {
        PaperVO paperVO= paperMapper.selectPaperById(id);
        for(PaperItem paperItem : paperVO.getQuestionItems()){
            for(QuestionVO questionVO : paperItem.getQuestionContent()){
                QuestionVO question = questionService.getQuestionById(questionVO.getId());
                questionVO.setPoint(question.getPoint());
                if(question.getItems() != null){
                    questionVO.setItems(question.getItems());
                }
            }
        }
        return paperVO;
    }

    @Override
    public void editPaper(PaperVO paperVO) {
        TPaper tPaper = new TPaper();
        BeanUtils.copyProperties(paperVO, tPaper);
        // 保存试卷信息
        paperMapper.updateById(tPaper);

        // 保存试卷里的题型分组
        // 先删除再添加
        QueryWrapper<TPaperItem> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", tPaper.getId());
        paperItemMapper.delete(wrapper);
        for(PaperItem paperItem : paperVO.getQuestionItems()){
            TPaperItem tPaperItem = new TPaperItem();
            BeanUtils.copyProperties(paperItem, tPaperItem);
            tPaperItem.setTitle(paperItem.getQuestionTitle());
            tPaperItem.setPaperId(tPaper.getId());
            paperItemMapper.insert(tPaperItem);
            // 保存题型分组-题目关系表
            // 先删除
            QueryWrapper<PaperItemToQuestion> wrapperForRelation = new QueryWrapper<>();
            wrapperForRelation.eq("paper_item_id", paperItem.getId());
            paperItemToQuestionMapper.delete(wrapperForRelation);
            for(QuestionVO questionVO : paperItem.getQuestionContent()){
                PaperItemToQuestion paperItemToQuestion = new PaperItemToQuestion();
                paperItemToQuestion.setPaperItemId(tPaperItem.getId());
                paperItemToQuestion.setQuestionId(questionVO.getId());
                paperItemToQuestionMapper.insert(paperItemToQuestion);
            }
        }

    }

    @Override
    public void deletePaper(String id) {
        // 先删paper
        paperMapper.deleteById(id);
        // 先将paper的题型类型所有id拿到
        QueryWrapper<TPaperItem> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", id);
        List<TPaperItem> paperItems = paperItemMapper.selectList(wrapper);
        for(TPaperItem paperItem : paperItems){
            QueryWrapper<PaperItemToQuestion> wrapperForRelation = new QueryWrapper<>();
            wrapperForRelation.eq("paper_item_id", paperItem.getId());
            paperItemToQuestionMapper.delete(wrapperForRelation);
        }
        // 删除题型类型
        paperItemMapper.delete(wrapper);
    }
}
