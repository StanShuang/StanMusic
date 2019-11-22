package com.stan.music.utils;

import android.content.Context;

/**
 * Author: Stan
 * Date: 2019/11/21 9:41
 * Description: ${DESCRIPTION}
 */
public class DensityUtil {
    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }
}
