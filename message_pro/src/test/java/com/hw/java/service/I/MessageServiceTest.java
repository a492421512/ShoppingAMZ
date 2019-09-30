package com.hw.java.service.I;

import com.hw.java.domain.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationcontext.xml")
public class MessageServiceTest {
    @Autowired
    private  MessageService msg;
    @Test
    public void sendMsg() {
    }

    @Test
    public void deleteMsgById() {
    }

    @Test
    public void findMessagesByReceiveid() {
        Message messageById = msg.findMessageById(1);
        System.out.println(messageById);
    }

    @Test
    public void findMessageById() {
        Message messageById = msg.findMessageById(4);
        System.out.println(messageById);
    }

    @Test
    public void queryLimitMsg() {
        List<Message> messages = msg.queryLimitMsg(1);
        System.out.println(messages);
    }


}