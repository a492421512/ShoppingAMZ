package com.hw.java.utils;

import com.hw.java.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCSelectUser {
    public static ArrayList<User> exSelect(String sql, Object...obj){
        ArrayList<User> list = new ArrayList();
        Connection  conn= null;
        PreparedStatement pstmt =null;
        ResultSet rs = null;
        try {
            //连接数据库连接池
            conn = JDBCPoolUtils1.getConnection();
            //创建执行SQL的对象
            pstmt =conn.prepareStatement(sql);
            //假设有占位符
            for (int i = 0; i < obj.length; i++) {
                pstmt.setObject(i+1,obj[i]);
            }
            //执行SQL语句获得结果集
            rs = pstmt.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt(1));//设置编号
                user.setName(rs.getString(2));//设置姓名
                user.setPassword(rs.getString(3));//设置密码
                user.setEmail(rs.getString(4));//设置邮箱
                list.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCPoolUtils1.closeMethod(rs,pstmt,conn);
        }
        return list;
    }
}
