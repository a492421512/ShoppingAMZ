package com.hw.java.service.I;

import com.hw.java.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationcontext.xml")
public class UserServiceTest {
    @Autowired
    private  UserService userService;
    @Test
    public void login() {
        /*ApplicationContext context = new ClassPathXmlApplicationContext("applicationcontext.xml");
        UserService bean = context.getBean(UserService.class);*/
        User user = new User();
        user.setName("wm123");
        user.setPassword("123");
        User login = userService.login(user);
        System.out.println(login);
    }

    @Test
    public void register() {
    }

    @Test
    public void getAllUsers() {
    }

    @Test
    public void findUserById() {
    }

    @Test
    public void query() {
    }
}