package com.xiangjuncheng.qgdalumni.common.Util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiangjuncheng.qgdalumni.model.bean.GroupEntity;

import java.util.List;

/**
 * Created by xiangjuncheng on 2016/2/24.
 */
public class ParseByGson {
    public static String make(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
