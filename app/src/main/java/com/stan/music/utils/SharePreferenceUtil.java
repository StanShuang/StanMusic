package com.stan.music.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.lzx.starrysky.provider.SongInfo;
import com.stan.music.Constants;
import com.stan.music.bean.LoginBean;

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
    public void saveAccountNum(String num){
        saveString(Constants.SpKey.PHONE_NUMBER,num);
    }
    public void saveUserInfo(LoginBean bean,String phoneNumber){
        if(bean.getBindings().size() > 1){
            setAuthToken(bean.getBindings().get(1).getTokenJsonStr());
        }
        saveAccountNum(phoneNumber);
        saveString(Constants.SpKey.USER_INFO,GsonUtil.toJson(bean));
    }
    public String  getUserInfo(String defaultValue){
        return getString(Constants.SpKey.USER_INFO,defaultValue);
    }
    /**
     * 存储最近一次听过的歌曲
     */
    public void saveLatestSong(SongInfo songInfo) {
        String song = GsonUtil.toJson(songInfo);
        saveString(Constants.SpKey.LATEST_SONG, song);
    }
    public SongInfo getLatestSong() {
        return GsonUtil.formJson(getString(Constants.SpKey.LATEST_SONG, ""), SongInfo.class);
    }

    private String getString(String key, String value) {
        return sp.getString(key,value);
    }
    private void saveString(String key, String value) {
        editor.putString(key,value).apply();
    }





}
