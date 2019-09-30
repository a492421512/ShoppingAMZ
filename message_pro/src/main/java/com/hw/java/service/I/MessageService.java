package com.hw.java.service.I;

import com.hw.java.domain.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageService {
    //发送短消息
    public int sendMsg(Message msg);
    //删除指定短消息
    public int deleteMsgById(int id);
    //查询所有接受的短消息
	public List<Message> findMessagesByReceiveid(int receiveid);
    //查找指定的短消息
    public Message findMessageById(int id);
    //分页查询
    public List<Message> queryLimitMsg(Integer receiveid);

}
