package com.hw.java.domain;
public class Message {
    private Integer id;//编号
    private Integer sendId;//发送人的编号
    private String title;//短信标题
    private String msgContent;//信息内容
    private Integer state;//信息状态
    private Integer receiveId;//接收人编号
    private String msgCreateDate;//发送短信的时间
    private User sendUser;//发件人信息

    public Message() {
    }

    public User getSendUser() {

        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    public Message(Integer id, Integer sendId, String title, String msgContent, Integer state, Integer receiveId, String msgCreateDate) {
        this.id = id;
        this.sendId = sendId;
        this.title = title;
        this.msgContent = msgContent;
        this.state = state;
        this.receiveId = receiveId;
        this.msgCreateDate = msgCreateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

    public String getMsgCreateDate() {
        return msgCreateDate;
    }

    public void setMsgCreateDate(String msgCreateDate) {
        this.msgCreateDate = msgCreateDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sendId=" + sendId +
                ", title='" + title + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", state=" + state +
                ", receiveId=" + receiveId +
                ", msgCreateDate='" + msgCreateDate + '\'' +
                ", sendUser=" + sendUser +
                '}';
    }
}
