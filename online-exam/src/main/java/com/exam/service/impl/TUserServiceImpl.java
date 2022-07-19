package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.base.RestResponse;
import com.exam.base.SystemCode;
import com.exam.entity.TUser;
import com.exam.entity.vo.UserVO;
import com.exam.exception.OnlineExamException;
import com.exam.mapper.TUserMapper;
import com.exam.service.TUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.utils.JwtUtils;
import com.exam.utils.MD5;
import com.exam.utils.Utils;
import com.mysql.cj.QueryResult;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-07-11
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

    @Autowired
    private TUserMapper userMapper;

    @Override
    public Page<UserVO> getPageList(Integer pageNum, Integer pageSize, Integer role,String keyword) {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.eq("role", role);
        wrapper.like("user_name", keyword);
        Page<TUser> tUserPage = new Page<>(pageNum,pageSize);
        baseMapper.selectPage(tUserPage, wrapper);
        Page<UserVO> userVOPage = new Page<>(pageNum,pageSize);

        // 转换为VO
        BeanUtils.copyProperties(tUserPage, userVOPage,"records");
        userVOPage.setRecords(new ArrayList<>());

        List<TUser> tUserList= tUserPage.getRecords();

        for(TUser tUser : tUserList){
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(tUser, userVO);
            if(tUser.getSex()){
                userVO.setSex("女");
            }else{
                userVO.setSex("男");
            }
            userVOPage.getRecords().add(userVO);
        }

        return userVOPage;
    }

    // 登录
    @Override
    public String login(String userName, String password) {
        // 判断非空
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            throw new OnlineExamException(SystemCode.LOGINERROR.getCode(), SystemCode.LOGINERROR.getMessage());
        }

        // 得到用户信息
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();

        wrapper.eq("user_name", userName);

        TUser tUser = baseMapper.selectOne(wrapper);

        if(!password.equals(tUser.getPassword())){
            throw new OnlineExamException(SystemCode.LOGINERROR.getCode(), SystemCode.LOGINERROR.getMessage());
        }

        // 生成token
        String token = JwtUtils.getJwtToken(tUser.getId(), tUser.getUserName());

        return token;
    }

    @Override
    public void addUser(TUser user) {
        // 判断用户名是否重复
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", user.getUserName());
        Integer count = baseMapper.selectCount(wrapper);
        if(count != 0){
            throw new OnlineExamException(SystemCode.USERNAME_ALREADYEXIST.getCode(), SystemCode.USERNAME_ALREADYEXIST.getMessage());
        }
        baseMapper.insert(user);
    }

    @Override
    public UserVO getStudentInfo() {
        String userId = Utils.getCurrentUserId(userMapper);
        TUser tUser = userMapper.selectById(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(tUser, userVO);
        userVO.setSex(tUser.getSex()+"");
        String birthDay = tUser.getBirthDay();
        String birthYear = birthDay.split("-")[0];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String nowYear = df.format(new Date()).split("-")[0];
//        userVO.setAge();
        Integer age = Integer.parseInt(nowYear) - Integer.parseInt(birthYear);
        userVO.setAge(age);
        return userVO;
    }

    @Override
    public void editStudent(UserVO userVO) {
        String userId = Utils.getCurrentUserId(userMapper);
        TUser tUser = userMapper.selectById(userId);
        TUser user = new TUser();
        tUser.setRealName(userVO.getRealName());
        tUser.setPhone(userVO.getPhone());
        tUser.setSex(Boolean.parseBoolean( userVO.getSex()));
        tUser.setBirthDay(userVO.getBirthDay());
        user.setId(userId);
        userMapper.updateById(tUser);
    }

    @Override
    public void editPassword(String oldPassword,String newPassword) {
        String studentId = Utils.getCurrentUserId(userMapper);
        TUser user = userMapper.selectById(studentId);
        String password = user.getPassword();
        oldPassword = MD5.encrypt(oldPassword);
        newPassword = MD5.encrypt(newPassword);
        if(!oldPassword.equals(password)){
            throw new OnlineExamException(SystemCode.OLD_PASSWORD_ERROR.getCode(), SystemCode.OLD_PASSWORD_ERROR.getMessage());
        }else{
            user.setPassword(newPassword);
            userMapper.updateById(user);
        }
    }
}
