package com.xiangjuncheng.qgdalumni;

import android.app.Application;

import com.xiangjuncheng.qgdalumni.common.Receiver.MyBroadcastReceiver;

import cn.bmob.newim.BmobIM;

/**
 * Created by xiangjuncheng on 2016/3/12.
 */
public class QGDApplication extends Application {

    private static QGDApplication INSTANCE;
    public static QGDApplication INSTANCE(){
        return INSTANCE;
    }
    private void setInstance(QGDApplication app) {
        setBmobIMApplication(app);
    }
    private static void setBmobIMApplication(QGDApplication a) {
        QGDApplication.INSTANCE = a;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        //im初始化
        BmobIM.init(this);
        //注册消息接收器
        BmobIM.registerDefaultMessageHandler(new MyBroadcastReceiver(this));
    }
}
