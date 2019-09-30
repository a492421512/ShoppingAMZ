package com.hwua.mapper;


import com.hwua.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface MsgMapper
{
    //查询所有短信
    @Transactional
    @Select("select id,sendid,title,msgcontent,state,receiveid,msg_create_date as msgCreateDate from messages ")
    public abstract List<Message> findAllMsg() throws Exception;


}
