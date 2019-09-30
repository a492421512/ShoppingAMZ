package com.hw.java.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil {
    private static SqlSessionFactory sqlSessionFactory = null;

    public static SqlSessionFactory getSqlSessionFactory(){
        if (sqlSessionFactory==null){
            synchronized (SqlSessionFactory.class){
                if (sqlSessionFactory==null){
                    try {
                        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
                        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession getSqlSession(){
        return getSqlSessionFactory().openSession();
    }
}
