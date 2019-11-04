package com.stan.music.activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hjq.toast.ToastUtils;
import com.stan.music.R;
import com.stan.music.base.BaseActivity;
import com.stan.music.base.BasePresenter;
import com.stan.music.utils.ClickUtil;
import com.stan.music.utils.InputUtil;
import com.stan.music.utils.ScreenUtils;
import com.stan.music.utils.SharePreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    String phoneNumber;
    String passWord;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initModule() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        setBackBtn(getString(R.string.colorBlack));
        setLeftTitleText(R.string.login_phone_number);
        if(!TextUtils.isEmpty(SharePreferenceUtil.getInstance(this).getAccountNum())){
            phoneNumber = SharePreferenceUtil.getInstance(this).getAccountNum();
            etPhone.setText(phoneNumber);
        }
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //设置状态栏为白体黑字
        ScreenUtils.setStatusBarColor(this, Color.parseColor("#FFFFFF"));
        ScreenUtils.setStatusBarDarkFont(this,true);
    }
    //TODO: 注册和忘记密码暂未实现
    @Override
    @OnClick({R.id.btn_login,R.id.register,R.id.forget_pwd})
    public void onClick(View v) {
        if (ClickUtil.isFastClick(1000,v)) {
            return;
        }
        switch (v.getId()){
            case R.id.btn_login:
                phoneNumber = etPhone.getText().toString();
                passWord = etPwd.getText().toString();
                if(InputUtil.checkMobileLegel(phoneNumber) && InputUtil.checkPasswordLegel(passWord)){

                }
                break;
            case R.id.register:
                ToastUtils.show(R.string.in_developing);
                break;
            case R.id.forget_pwd:
                ToastUtils.show(R.string.in_developing);
                break;
        }
    }
}
