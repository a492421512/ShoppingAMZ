package com.hw.java.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*
数据库连接池
*/
public class JDBCPoolUtils1 {
   /* public static void main(String[] args) throws Exception {
        *//*连接数据库连接池*//*
        Connection conn = JDBCPoolUtils1.getConnection();
        String sql = "select * from ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,"users");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            System.out.println("我执行了");
            int id = rs.getInt("id");
            System.out.println(id);
        }
    }*/
    private static DataSource ds;

    //静态代码块，加载类后自动连接数据库连接池
    static{
        try {
            //创建properties对象读取配置文件
            Properties pro = new Properties();
            //获取配置文件的路径
            InputStream is = JDBCPoolUtils1.class.getClassLoader().getResourceAsStream("druid.properties");
            //读取配置文件
            pro.load(is);
            //连接数据库连接池
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取数据库连接
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //关闭资源
    public static void closeMethod(ResultSet rs,Statement stmt,Connection conn){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn !=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeMethod(Statement stmt,Connection conn){
        closeMethod(null,stmt,conn);
    }
}
