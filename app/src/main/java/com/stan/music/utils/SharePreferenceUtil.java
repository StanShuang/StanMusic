package com.stan.music.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.stan.music.Constants;

/**
 * Author: Stan
 * Date: 2019/11/4 10:43
 * Description: 本地SharePreference工具
 */
public class SharePreferenceUtil {
    private static SharePreferenceUtil mInstance;
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public SharePreferenceUtil() {
    }
    @SuppressLint("CommitPrefEdits")
    private static void init(Context context) {
        if(sp == null){
            sp =context.getSharedPreferences(Constants.SHARED_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        }
        editor = sp.edit();
    }
    public static SharePreferenceUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SharePreferenceUtil.class){
                if(mInstance == null){
                    init(context);
                    mInstance = new SharePreferenceUtil();
                }
            }
        }
        return mInstance;
    }
    private void setAuthToken(String token){
        saveString(Constants.SpKey.AUTH_TOKEN,token);
    }


    /**
     *获取AuthToken
     * @param defaultValue
     * @return
     */
    public String getAuthToken(String defaultValue){
        return getString(Constants.SpKey.AUTH_TOKEN,defaultValue);
    }
    /**
     * 获取账户名
     * @return
     */
    public String getAccountNum(){
        return getString(Constants.SpKey.PHONE_NUMBER,"");
    }
    public void setAccountNum(String num){
        saveString(Constants.SpKey.PHONE_NUMBER,num);
    }
    private String getString(String key, String value) {
        return sp.getString(key,value);
    }
    private void saveString(String key, String value) {
        editor.putString(key,value).apply();
    }





}
