package com.stan.music.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Author: Stan
 * Date: 2019/11/20 10:34
 * Description: ${DESCRIPTION}
 */
public class MathUtils {
    private static int numBasic = 100000;
    private static String numUnit = "万";
    public static String mathFormat(int num){
        if(num >= numBasic){
            DecimalFormat df = new DecimalFormat("#.0");
            df.setRoundingMode(RoundingMode.UP);
            return df.format((double) num/10000) + numUnit;
        }else{
            return String.valueOf(num);
        }
    }
}
