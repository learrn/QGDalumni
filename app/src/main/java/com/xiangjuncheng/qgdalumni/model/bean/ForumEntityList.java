package com.xiangjuncheng.qgdalumni.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by XJC on 2016/3/24.
 */
public class ForumEntityList extends BmobObject {
    private String postAccount;
    private String postName;
    private String title;
    private String content;
    private String recNum;
    private String time;
    private int type;
    private int id;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostAccount() {
        return postAccount;
    }

    public void setPostAccount(String postAccount) {
        this.postAccount = postAccount;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecNum() {
        return recNum;
    }

    public void setRecNum(String recNum) {
        this.recNum = recNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
