package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.vo.UserVO;
import io.swagger.models.auth.In;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-11
 */
public interface TUserService extends IService<TUser> {


    Page<UserVO> getPageList(Integer pageNum, Integer pageSize, Integer role,String keyword);

    // 登录
    String login(String userName, String password);

    void addUser(TUser user);

    UserVO getStudentInfo();

    void editStudent(UserVO userVO);

    void editPassword(String oldPassword,String newPassword);
}
