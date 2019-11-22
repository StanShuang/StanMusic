package com.stan.music;

import android.app.Application;

import com.hjq.toast.ToastUtils;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.StarrySkyConfig;


/**
 * Author: Stan
 * Date: 2019/11/4 14:40
 * Description: ${DESCRIPTION}
 */
public class App extends Application {
    private final String TAG = "App";
    private static App mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        StarrySky.init(this,new MusicConfig());
        ToastUtils.init(this);
    }
    private static class  MusicConfig extends StarrySkyConfig{

    }
    public static App getContext() {
        return mContext;
    }
}
