package com.stan.music.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stan.music.bean.LoginBean;

/**
 * Author: Stan
 * Date: 2019/11/6 10:26
 * Description: 字符串和Java类的转换工具
 */
public class GsonUtil {
    private final static String TAG = "GsonUtil";

    public static String toJson(Object obj) {
        String result = creatGson().toJson(obj);
        LogUtil.d(TAG, "toJson: " + result);
        return result;
    }

    private static Gson creatGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        return gsonBuilder.create();
    }

    public static <T> T formJson(String json, Class<T> cls) {
        try {
            return creatGson().fromJson(json,cls);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
