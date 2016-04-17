package com.xiangjuncheng.qgdalumni.common;

import cn.bmob.v3.BmobUser;

public class User_info extends BmobUser implements java.io.Serializable {
    String name;
    String collage;
    int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
