package com.hw.java.service;


import com.hw.java.domain.User;
import com.hw.java.mapper.UserMapper;
import com.hw.java.service.I.UserService;
import com.hw.java.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 登陆业务
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        try {
            user = userMapper.queryByUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 注册业务
     *
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        int flag = 0;
        try {
            flag = userMapper.insert(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag > 0 ? true : false;
    }

    /**
     * 获取所有的用户
     *
     * @return
     */
    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try {
            list = userMapper.queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    @Override
    public User findUserById(int id) {
        User user = null;
        try {
            user = userMapper.queryById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 根据用户姓名查找用户信息
     *
     * @param name
     * @return
     */
    @Override
    public User query(String name) {
        User user = null;
        try {
            user = userMapper.queryByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


}
