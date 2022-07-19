package com.exam.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.TTask;
import com.exam.entity.TUser;
import com.exam.entity.vo.TaskVO;
import com.exam.mapper.TUserMapper;
import com.exam.service.TTaskService;
import com.exam.utils.Utils;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-10
 */
@RestController
@RequestMapping("/task")
@CrossOrigin
public class TTaskController {

    @Autowired
    private TTaskService taskService;

    @Autowired
    private TUserMapper userMapper;

    @DeleteMapping("/deleteTask/{id}")
    public RestResponse<String> deleteTask(@PathVariable("id") String id) {
        try {
            taskService.removeById(id);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @PutMapping("/editTask")
    public RestResponse<String> editTask(@RequestBody TaskVO task) {
        TTask tTask = new TTask();
        BeanUtils.copyProperties(task, tTask);
        try {
            taskService.updateById(tTask);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @GetMapping("/getTaskById/{id}")
    public RestResponse<TaskVO> getTaskById(@PathVariable("id") String id) {
        TaskVO task = new TaskVO();
        try {
            TTask tTask = taskService.getById(id);
            BeanUtils.copyProperties(tTask, task);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(task);

    }

    @PostMapping("/addTask")
    public RestResponse<String> addTask(@RequestBody TTask task) {
        try {
            String levelName = Utils.changeLevelName(task.getLevel());
            task.setLevelName(levelName);
            taskService.save(task);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @GetMapping("/page/taskList")
    public RestResponse<Page<TaskVO>> userTaskList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "level", required = false) String level) {

        Page<TaskVO> taskList = null;

        try {
            TUser user = Utils.getCurrentUser(userMapper);
            level = user.getUserLevel() + "";
            taskList = taskService.getPageList(pageNum, pageSize, keyword, level);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok(taskList);

    }

    // 用于后台显示任务列表
    @GetMapping("/page/list")
    public RestResponse<Page<TaskVO>> pageList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "level", required = false) String level) {

        Page<TaskVO> taskList = null;

        try {

            taskList = taskService.getPageList(pageNum, pageSize, keyword, level);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok(taskList);
    }

    @GetMapping("/stu/unfinishedTask")
    public RestResponse<List<TaskVO>>getUnfinishedTask(){
        List<TaskVO> tasks = null;
        try {
            tasks = taskService.getUnfinishedTask();
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(tasks);
    }

    @GetMapping("/stu/page/list")
    public RestResponse<Page<TaskVO>> stuPageList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "subjectId",required = false) String subjectId) {
        Page<TaskVO> taskList = null;

        try {

            taskList = taskService.getStuPageList(pageNum, pageSize, keyword,subjectId);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }

        return RestResponse.ok(taskList);
    }
}

