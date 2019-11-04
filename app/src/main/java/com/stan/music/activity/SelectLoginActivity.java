package com.stan.music.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;

import com.hjq.toast.ToastUtils;
import com.stan.music.R;
import com.stan.music.base.BaseActivity;
import com.stan.music.base.BasePresenter;
import com.stan.music.utils.ClickUtil;
import com.stan.music.utils.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectLoginActivity extends BaseActivity {
    private static String TAG = "SelectLoginActivity";
    private long firstTime;
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_select);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScreenUtils.setStatusBarColor(this, ContextCompat.getColor(this,R.color.colorWindowStatus));
    }

    @Override
    protected void initModule() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    @OnClick(R.id.btn_phone_login)
    public void onClick(View v) {
        if(ClickUtil.isFastClick(1000,v)){
            return;
        }
        switch (v.getId()){
            case R.id.btn_phone_login:
                startActivity(LoginActivity.class,null,false);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){//点击返回键
            exitApp(2000);//退出应用
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出应用
     * @param time 时间间隔
     */
    private void exitApp(int time) {
        if(System.currentTimeMillis() - firstTime > time){
            ToastUtils.show(R.string.press_exit_again);
            firstTime = System.currentTimeMillis();
        }else {
            finish();//销毁当前activity
            System.exit(0);
        }
    }
}
