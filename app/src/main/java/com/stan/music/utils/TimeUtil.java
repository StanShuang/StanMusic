package com.stan.music.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Author: Stan
 * Date: 2019/11/22 17:33
 * Description: 时间转换工具
 */
public class TimeUtil {
    //输入时间戳，返回分秒的时间格式
    public static String getTimeNoYMDH(long time){
        SimpleDateFormat format = new SimpleDateFormat("mm:ss.SSS", Locale.getDefault());
        return format.format(time);
    }
}
