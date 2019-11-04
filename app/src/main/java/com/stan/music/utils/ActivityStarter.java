package com.stan.music.utils;

import android.app.Activity;
import android.content.Intent;

import com.stan.music.activity.MainActivity;
import com.stan.music.activity.SelectLoginActivity;

/**
 * Author: Stan
 * Date: 2019/11/4 10:32
 * Description: ${DESCRIPTION}
 */
public class ActivityStarter {
    private static ActivityStarter intstance;

    public ActivityStarter() {
    }
    public static synchronized ActivityStarter getInstance(){
        if(intstance == null){
            intstance = new ActivityStarter();
        }
        return intstance;
    }

    /**
     * 启动Activity,同时清空栈中的activity，栈中只留下启动的activity
     * @param activity
     */
    public void startMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    public void startLoginActivity(Activity activity){
        Intent intent = new Intent(activity, SelectLoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
