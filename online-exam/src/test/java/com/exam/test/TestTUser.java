package com.exam.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.TUser;
import com.exam.entity.vo.UserVO;
import com.exam.service.TUserService;
import com.exam.utils.MD5;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : TestTUser
 * @Description :
 * @Author : y
 * @Date: 2021-07-11 09:52
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTUser {

    @Autowired
    private TUserService tUserService;

    @Test
    public void testGetPageList() {
        Page<UserVO> page = tUserService.getPageList(1, 3, 2, "");
        System.out.println(page.getCurrent());//当前页
        System.out.println(page.getRecords());//当前数据list集合
        System.out.println(page.getSize());//每页显示记录数
        System.out.println(page.getTotal());// 总记录数
        System.out.println(page.getPages());// 总页数

//        System.out.println(pageList);
    }

    @Test
    public void testInsert() {
//        for(int i = 1;i < 20; i++) {
        TUser tUser = new TUser();
        tUser.setUserName("test");
        tUser.setPassword(MD5.encrypt("123456"));

        tUserService.save(tUser);
//        }
    }
}
