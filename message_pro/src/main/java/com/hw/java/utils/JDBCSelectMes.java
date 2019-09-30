package com.hw.java.utils;

import com.hw.java.domain.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCSelectMes {
    public static ArrayList<Message> exSelect(String sql, Object...obj){
        ArrayList<Message> list = new ArrayList();
        Connection conn= null;
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
                Message mes = new Message();
                mes.setId(rs.getInt(1));//设置编号
                mes.setSendId(rs.getInt(2));//设置发送人编号
                mes.setTitle(rs.getString(3));//设置短信标题
                mes.setMsgContent(rs.getString(4));//设置信息内容
                mes.setState(rs.getInt(5));//设置信息状态
                mes.setReceiveId(rs.getInt(6));//设置接收人编号
                mes.setMsgCreateDate(rs.getString(7));//设置发送时间
                list.add(mes);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JDBCPoolUtils1.closeMethod(rs,pstmt,conn);
        }
        return list;
    }
}
