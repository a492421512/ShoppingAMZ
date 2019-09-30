package com.hw.java.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUpdate1 {
    public static int exUpdate(String sql,Object...obj){
        Connection conn = null;
        PreparedStatement pstmt =null;
        try {
            //连接数据库连接池,获取数据库连接
            conn = JDBCPoolUtils1.getConnection();
            //开启事务
            conn.setAutoCommit(false);
            //创建PreparedStatement对象执行SQL语句
            pstmt = conn.prepareStatement(sql);
            //假设sql语句有占位符
            //循环不定长参数
            for (int i = 0; i < obj.length; i++) {
                //设置占位符
                pstmt.setObject(i+1,obj[i]);
            }
            //执行SQL语句
            int count = pstmt.executeUpdate();
            //提交事务
            conn.commit();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            //回滚
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            //断开连接
            JDBCPoolUtils1.closeMethod(pstmt,conn);
        }
        return 0;
    }
}
