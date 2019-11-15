package com.stan.music.splash;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;

import com.stan.music.R;
import com.stan.music.base.BaseActivity;
import com.stan.music.base.BasePresenter;
import com.stan.music.utils.ActivityStarter;
import com.stan.music.utils.ScreenUtils;
import com.stan.music.utils.SharePreferenceUtil;

public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //隐藏导航栏和状态栏。
        View decorView = getWindow().getDecorView();
        // View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_FULLSCREEN
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_splash);


    }

    @Override
    protected void initModule() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //绘制导航栏的颜色
        //TODO: 这里和设置透明状态栏相关联，若开屏界面颜色多样化，可以不设置导航栏透明与颜色，只需要布局中使用属性android:fitsSystemWindows="true"即可达到全屏的效果
        ScreenUtils.setStatusBarColor(this, ContextCompat.getColor(this,R.color.colorWindowStatus));
    }

    @Override
    protected void initData() {
        startCountDownTime();

    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    private void startCountDownTime() {
        countDownTimer = new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                String authToken = SharePreferenceUtil.getInstance(SplashActivity.this).getAuthToken("");
                if(authToken.isEmpty()){
                    ActivityStarter.getInstance().startLoginActivity(SplashActivity.this);
                }else{
                    ActivityStarter.getInstance().startMainActivity(SplashActivity.this);
                }
                SplashActivity.this.finish();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void finish() {
        super.finish();
        if(countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
