package com.hw.java.service.I;

import com.hw.java.domain.User;

import java.util.List;

public interface UserService {
    public User login(User user);//登录业务
    public boolean register(User user);//注册业务
    public List<User> getAllUsers();//获取所有的用户
    public User findUserById(int id);//根据用户id查询用户信息
    public User query(String name);//通过用户姓名查找用户信息
}
