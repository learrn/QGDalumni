package com.xiangjuncheng.qgdalumni.common.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiangjuncheng on 2016/2/22.
 */
public class TimeUtils {
    public static String geTimeNoS(){
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("MM-dd HH:mm");
        String time=df.format(date);
        return time;
    }
    public static String geTime(){
        Date date=new Date();
        SimpleDateFormat df=new SimpleDateFormat("MM-dd HH:mm:ss");
        String time=df.format(date);
        return time;
    }
}
