package com.stan.music;

import android.app.Application;

import com.hjq.toast.ToastUtils;

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
        ToastUtils.init(this);
    }

    public static App getmContext() {
        return mContext;
    }
}
