package com.xiangjuncheng.qgdalumni.common;

/**
 * Created by xiangjuncheng on 2016/2/15.
 */
public class QGDMessage implements java.io.Serializable{
    public static final String SUCCESS="1";//表明是否成功
    public static final String FAIL="2";//表明失败
    public static final String COM_MES="3";//普通信息包
    public static final String GET_ONLINE_FRIENDS="4";//要求在线好友的包
    public static final String RET_ONLINE_FRIENDS="5";//返回在线好友的包
    public static final String LOGIN="7";//请求验证登陆
    public static final String REGISTER="8";//请求注册
    public static final String GET_CHAT="9";//请求聊天记录的包
    public static final String RET_CHAT="10";//返回聊天记录的包
    public static final String GET_FORUM="11";//请求论坛信息的包
    public static final String RET_FORUM="12";//返回论坛信息的包
    public static final String GET_FORUM_REC="13";//请求论坛回复的包
    public static final String RET_FORUM_REC="14";//返回论坛回复的包
    public static final String FORUM_NEW_LIST="15";//论坛新建帖子的包
    public static final String FORUM_NEW_REC="16";//论坛帖子新回复的包

    String type;
    String sender;
    String senderNick;
    int senderAvatar;
    String receiver;
    String content;
    String sendTime;
    String classYear;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getSenderNick() {
        return senderNick;
    }
    public void setSenderNick(String senderNick) {
        this.senderNick = senderNick;
    }
    public int getSenderAvatar() {
        return senderAvatar;
    }
    public void setSenderAvatar(int senderAvatar) {
        this.senderAvatar = senderAvatar;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSendTime() {
        return sendTime;
    }
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getClassYear() {
        return classYear;
    }

    public void setClassYear(String classYear) {
        this.classYear = classYear;
    }
}
