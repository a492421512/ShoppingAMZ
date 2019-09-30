package com.hw.java.mapper;

import com.hw.java.domain.User;
import com.hw.java.service.I.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.sql.SQLException;
import java.util.List;


public class UserMapperTest {


    @Test
    public void insert() {
    }

    @Test
    public void queryByUser() {
    }

    @Test
    public void queryByName() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext.xml");
        UserService userService1 = context.getBean(UserService.class);
        User wm123 = userService1.query("wm123");
        System.out.println(wm123);
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }

    @Test
    public void queryAll() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext.xml");
        UserService userService1 = context.getBean(UserService.class);
        List<User> users = userService1.getAllUsers();
        System.out.println(users);
    }

    @Test
    public void queryById() {
    }
}