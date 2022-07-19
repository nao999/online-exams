package com.exam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.TUser;
import com.exam.entity.User;
import com.exam.entity.vo.UserVO;
import com.exam.exception.OnlineExamException;
import com.exam.service.StudentLogService;
import com.exam.service.TUserService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.net.www.protocol.http.HttpURLConnection;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-11
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class TUserController {

    @Autowired
    private TUserService userService;

    @Autowired
    private StudentLogService studentLogService;

    Logger logger = LoggerFactory.getLogger(TUserController.class);

    // 记录登出
    @PostMapping("/logoutSave")
    public RestResponse<String> logoutSave(){
        studentLogService.logLogout();
        return RestResponse.ok();
    }

    // 记录登录
    @PostMapping("/loginSave")
    public RestResponse<String> loginSave(){
        studentLogService.logLogin();
        return RestResponse.ok();
    }

    @PutMapping("/changePassword")
    public RestResponse<String> changePassword(
            @RequestBody Map<String,String> map){
        try {
            String oldPassword = map.get("oldPassword");
            String newPassword = map.get("newPassword");
            userService.editPassword(oldPassword,newPassword);
        } catch (OnlineExamException e) {
            return RestResponse.fail(e.getCode(), e.getMsg());
        }
        return RestResponse.ok();
    }

    @PutMapping("/editStudent")
    public RestResponse<String> editStudent(@RequestBody UserVO userVO){
        try {
            userService.editStudent(userVO);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @GetMapping("/getStudentInfo")
    public RestResponse<UserVO> getStudentInfo(){
        UserVO studentInfo = null;
        try {
            studentInfo = userService.getStudentInfo();
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok(studentInfo);
    }

    @DeleteMapping("/deleteUser/{id}")
    public RestResponse<String> deleteUser(@PathVariable String id){
        try {
            QueryWrapper<TUser> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            userService.remove(wrapper);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(),SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    @PutMapping("/editUser/{id}")
    public RestResponse<String> editUser(@RequestBody TUser user,@PathVariable String id){

        try {
            QueryWrapper<TUser> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            userService.update(user,wrapper);
        } catch (Exception e) {
            return RestResponse.fail(SystemCode.InnerError.getCode(),SystemCode.InnerError.getMessage());
        }
        return RestResponse.ok();
    }

    // 根据id获得用户
    @GetMapping("/getUserById/{id}")
    public RestResponse<TUser> getUserById(@PathVariable String id){
        TUser user = userService.getById(id);
        return RestResponse.ok(user);
    }

    @PostMapping("/addUser")
    public RestResponse<String> addUser(@RequestBody TUser user){
        try {
            userService.addUser(user);
        } catch (OnlineExamException e) {
            return RestResponse.fail(e.getCode(),e.getMsg());
        }
        return RestResponse.ok();
    }

    @PostMapping("/login")
    public RestResponse<String> login(@RequestBody TUser user){

        String userName = user.getUserName();
        String password = user.getPassword();
        String token = "";
        try {
            token = userService.login(userName,password);
        } catch (OnlineExamException e) {
            return RestResponse.fail(e.getCode(),e.getMsg());
        }
        return RestResponse.ok(token);
    }

    // 显示用户信息
    @GetMapping("/page/list")
    public RestResponse<Page<UserVO>> pageList(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("role") Integer role,
            @RequestParam(value = "keyword",required = false) String keyword){
        Page<UserVO> userList = null;
        try {
            userList = userService.getPageList(pageNum, pageSize, role,keyword);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return RestResponse.fail(SystemCode.InnerError.getCode(), SystemCode.InnerError.getMessage());
        }
        RestResponse<Page<UserVO>> restResponse = RestResponse.ok(userList);
        return restResponse;
    }


}

