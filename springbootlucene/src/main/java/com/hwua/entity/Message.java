package com.hwua.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Integer id;//编号
    private Integer sendId;//发送人的编号
    private String title;//短信标题
    private String msgContent;//信息内容
    private Integer state;//信息状态
    private Integer receiveId;//接收人编号
    private String msgCreateDate;//发送短信的时间
}
