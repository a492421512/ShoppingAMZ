package com.hwua.mapper;

import com.hwua.entity.User;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;

public interface UserDao {
    public abstract User queryUser(@Param("uname") String uname, @Param("pwd") String pwd) throws SQLException;//通过姓名，密码查找
    public abstract Integer insertUser(User user) throws SQLException;//添加用户信息
    public abstract User queryName(String name) throws SQLException;//通过姓名查询用户
    public abstract User queryUserById(Long id) throws SQLException;//通过id查询用户
    public abstract User queryUserByEmail(@Param("uname") String uname, @Param("email") String email) throws SQLException;//通过姓名和邮箱查询
}
