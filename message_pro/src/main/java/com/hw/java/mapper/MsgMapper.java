package com.hw.java.mapper;

import com.hw.java.domain.Message;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

public interface MsgMapper {
    //插入短消息
    public int insert(Message msg) throws SQLException;
    //查询接收者收到的所有短消息
    public List<Message> queryAll(int receiveid) throws SQLException;
    //查询指定id对应的短消息
    public Message query(int id) throws SQLException;
    //删除短消息
    public int delete(int id)throws SQLException;
    //更新短消息
    public int update(int id) throws SQLException;
    //分页查询
    public List<Message> queryLimitMsg(Integer receiveid)throws SQLException;
}
