package com.hwua.service;

import com.hwua.entity.User;
import com.hwua.service.I.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserServiceImplTest {
    @Autowired
    private UserService userS;
    @Test
    public void login() throws SQLException {
        User wm123 = userS.login("wm", "123");
        System.out.println(wm123);
    }
    @Test
    public void num() throws SQLException {
        Integer[] arr = {0,0,4,2,5,0,3,0};
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(arr));
        int k = 0;
        Integer zero = new Integer(0);
        while(k<list.size()){
            if (list.get(k).equals(zero)){
                list.remove(k);
            }
            System.out.println(list);
            k++;
        }
        System.out.println(list);
    }

    @Test
    public void register() throws SQLException {
        Date date = new Date();
        User user = new User();
        user.setUname("wm123445");
        user.setPwd("123");
        user.setSex(1);
        user.setBirthday(date);
        user.setIdcard("123456");
        user.setEmail("123@qq.com");
        user.setMobile("123");
        user.setAddress("湖南");

        boolean register = userS.register(user);
        System.out.println(register);
    }
}