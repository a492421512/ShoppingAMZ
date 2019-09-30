package com.hw.java.service;


import com.hw.java.domain.Message;
import com.hw.java.domain.User;
import com.hw.java.mapper.MsgMapper;
import com.hw.java.service.I.MessageService;
import com.hw.java.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MsgMapper msgMapper;

    /**
     * 发送短消息
     *
     * @param msg
     * @return
     */
    @Override
    public int sendMsg(Message msg) {
        int res = 0;
        try {
            res = msgMapper.insert(msg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 删除指定短消息
     *
     * @param id
     * @return
     */
    @Override
    public int deleteMsgById(int id) {
        int res = 0;
        try {
            res = msgMapper.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 查询所有短消息
     *
     * @param receiveid
     * @return
     */
    @Override
    public List<Message> findMessagesByReceiveid(int receiveid) {

        List<Message> list = null;
        try {
            list = msgMapper.queryAll(receiveid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }


    /**
     * 查找指定的短消息
     *
     * @param id
     * @return
     */
    @Override
    public Message findMessageById(int id) {
        Message msg = null;
        try {
            msgMapper.update(id);
            msg = msgMapper.query(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return msg;

    }


    /**
     * 分页查询
     *
     * @param receiveid
     * @return
     */
    @Override
    public List<Message> queryLimitMsg(Integer receiveid) {

        List<Message> list = null;
        try {
            list = msgMapper.queryLimitMsg(receiveid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
