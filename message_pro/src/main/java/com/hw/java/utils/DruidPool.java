package com.hw.java.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DruidPool {
    private  static DataSource ds ;
    private static ThreadLocal<Connection> tc = new ThreadLocal<>();//获得本地线程类

    //初始化连接池
    public static DataSource getDruidDS(){
        try {
            //获取配置文件路径
            InputStream rs = DruidPool.class.getClassLoader().getResourceAsStream("druid.properties");
            //创建properties对象读取文件
            Properties prop = new Properties();
            prop.load(rs);
            //连接数据库连接池
            ds = DruidDataSourceFactory.createDataSource(prop);
            return ds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    //获取一个连接对象,第一次获取是从数据源中取一个绑定到线程上,后面直接从线程上去获取绑定的连接对象
    public static Connection getConnection() throws SQLException {
        Connection conn = tc.get();
        //如果是第一次连接，需要创建一个线程
        if (conn==null){
            //就获取数据库连接
            conn = ds.getConnection();
            //把Values放入到ThreadLocal
            tc.set(conn);
        }
        return conn;
    }

    //开启事务
    public static void startTransaction() throws SQLException{
        Connection conn = getConnection();//从线程中获取变量副本
        conn.setAutoCommit(false);//开启事务
    }

    //提交事务
    public static void commit() throws SQLException{
        Connection conn = getConnection();//从线程中获取变量副本
        conn.commit();//提交事务
        conn.close();//关闭连接池
        tc.remove();//删除本地线程上绑定的数据
    }

    //回滚
    public static void rollback() throws SQLException{
        Connection conn = getConnection();//从线程中获取变量副本
        conn.rollback();//回滚事务
        conn.close();//关闭连接池
    }

}