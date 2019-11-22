package com.stan.music.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stan.music.bean.LoginBean;

import java.util.Hashtable;

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
    public static Hashtable<String,String> getLyricsAndTime(String str){
        Hashtable<String,String> tab = new Hashtable<>();
        int startIndex = -1;
        while ((startIndex = str.indexOf("[",startIndex + 1)) != -1){
            int endIndex = str.indexOf("]",startIndex+1);
            String key = str.substring(startIndex+1,endIndex);
            if(str.indexOf("[",endIndex) != -1){
                startIndex = str.indexOf("[",endIndex) -1;
            }else{
                startIndex = str.length()-1;
            }

            String value = str.substring(endIndex+1,startIndex+1);
            LogUtil.d(TAG,key+"-----"+ value);
            tab.put(key,value);
        }
        return tab;
    }
}
