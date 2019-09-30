package com.hwua.mapper;

import com.hwua.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MsgMapperTest {
    @Autowired
    private ApplicationContext context;
    @Test
    public void findAllMsg() throws Exception {
        MsgMapper bean = context.getBean(MsgMapper.class);
        List<Message> allMsg = bean.findAllMsg();
        System.out.println(allMsg);
    }
}