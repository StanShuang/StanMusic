package com.stan.music.utils;

import android.util.SparseArray;
import android.view.View;

/**
 * Author: Stan
 * Date: 2019/11/4 14:11
 * Description: ${DESCRIPTION}
 */
public class ClickUtil {
    //两次按钮点击时间间隔不能超过minDelayTime
    private static SparseArray<Long> lastSparseArray = new SparseArray<>();
    public static boolean isFastClick(int minDelayTime, View view){
        long curClickTime = System.currentTimeMillis();
        long lastClickTime = lastSparseArray.get(view.getId(),-1L);
        if(curClickTime - lastClickTime > minDelayTime){
            lastSparseArray.put(view.getId(),curClickTime);
            return false;
        }else{
            return true;
        }

    }

}
