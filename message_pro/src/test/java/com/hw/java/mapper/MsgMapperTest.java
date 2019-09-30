package com.hw.java.mapper;

import com.hw.java.domain.Message;
import com.hw.java.service.I.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;

;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationcontext.xml")
public class MsgMapperTest {

    @Autowired
    private MessageService msgMapper;
    @Test
    public void insert() {
    }

    @Test
    public void queryAll() throws SQLException {

    }

    @Test
    public void query() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }

    @Test
    public void queryLimitMsg() throws SQLException {
        List<Message> messages = msgMapper.queryLimitMsg(1);
        System.out.println(messages);
    }
}