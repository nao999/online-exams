package com.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.TSubject;
import com.exam.entity.TUser;
import com.exam.entity.vo.UserVO;
import com.exam.service.TSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sun.security.timestamp.TSRequest;

import javax.security.auth.Subject;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-19
 */
@RestController
@RequestMapping("/subject")
@CrossOrigin
public class TSubjectController {
    @Autowired
    private TSubjectService subjectService;

    // 根据年级返回所有的学科
    @GetMapping("/getAllSubject")
    public RestResponse<List<TSubject>> getAllSubject(@RequestParam("level") Integer level){
        QueryWrapper<TSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("level", level);
        List<TSubject> subjectList = subjectService.list(wrapper);
        return RestResponse.ok(subjectList);
    }

    @DeleteMapping("/deleteSubject/{id}")
    public RestResponse<String> deleteSubject(@PathVariable String id){
        try {
            QueryWrapper<TSubject> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            subjectService.remove(wrapper);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(),SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @PutMapping("/editSubject/{id}")
    public RestResponse<String> editUser(@RequestBody TSubject subject,@PathVariable String id){

        try {
            QueryWrapper<TSubject> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            subjectService.update(subject,wrapper);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(),SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @PostMapping("/addSubject")
    public RestResponse<String> addSubject(@RequestBody TSubject tSubject){
        try {
            subjectService.save(tSubject);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    // 根据id获得用户
    @GetMapping("/getSubjectById/{id}")
    public RestResponse<TSubject> getSubjectById(@PathVariable String id){
        TSubject subject = subjectService.getById(id);
        return RestResponse.ok(subject);
    }

    @GetMapping("/page/list")
    public RestResponse<Page<TSubject>> pageList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("level") Integer level) {
        Page<TSubject> subjectvoPage = null;
        try {
            subjectvoPage = subjectService.getPageList(pageNum, pageSize,level);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok(subjectvoPage);
    }
}

