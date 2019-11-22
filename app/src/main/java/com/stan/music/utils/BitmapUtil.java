package com.stan.music.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;

/**
 * Author: Stan
 * Date: 2019/11/20 11:19
 * Description: ${DESCRIPTION}
 */
public class BitmapUtil {
    /**
     * 该方法用url申请一个图片bitmap，并将其压缩成原图1/300，计算上半部分和下半部分颜色RGB平均值
     * 两个RGB去作为渐变色的两个点
     * 还要开子线程去计算...
     */
    public static Bitmap calculateColorsBitmap(String url) {
        //渐变色的两个颜色
        Bitmap bitmap = null;
        try {
            URL fileUrl;
            fileUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inSampleSize = 400;
            bitmap = BitmapFactory.decodeStream(is, new Rect(), opt);
            is.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
