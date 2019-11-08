package com.stan.music.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.stan.music.App;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Author: Stan
 * Date: 2019/11/5 15:26
 * Description: ${DESCRIPTION}
 */
public class NetUtil {
    /**
     * 判断网络是否连接
     * @return
     */
    public static boolean isConnected(){
        ConnectivityManager connectivity = (ConnectivityManager) App.getContext()
                .getSystemService(CONNECTIVITY_SERVICE);

        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
