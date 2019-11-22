package com.stan.music.utils;

import android.text.TextUtils;

import com.hjq.toast.ToastUtils;
import com.stan.music.R;

/**
 * Author: Stan
 * Date: 2019/11/4 17:30
 * Description: 检查输入合法性
 */
public class InputUtil {
    /**
     * 检查手机号的合法性
     * @param num
     */
    public static boolean checkMobileLegel(String num){
        if(TextUtils.isEmpty(num)){
            ToastUtils.show(R.string.please_input_phone_number);
            return false;
        }else if(num.length() != 11){
            ToastUtils.show(R.string.please_input_phone_number_correctly);
            return false;
        }
        return true;
    }

    /**
     * 检查密码的合法性
     * @param pwd
     * @return
     */
    public static boolean checkPasswordLegel(String pwd){
        if(TextUtils.isEmpty(pwd)){
            ToastUtils.show(R.string.please_input_pwd);
            return false;
        }else if(pwd.length()  >= 30){
            ToastUtils.show(R.string.pwd_must_less_than_30);
            return false;
        }
        return true;
    }
    /**
     * 检查输入的合法性
     */
    public static  boolean checkInputLegel(String str,int length){
        if(TextUtils.isEmpty(str)){
            ToastUtils.show("请输入标题!");
            return false;
        }else if(str.length() > length){
            ToastUtils.show("请输入小于"+length+"位的标题!");
            return false;
        }
        return true;
    }
}
