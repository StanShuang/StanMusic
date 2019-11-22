package com.stan.music.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stan.music.R;
import com.stan.music.widget.LodingDialog;

/**
 * Author: Stan
 * Date: 2019/11/4 9:47
 * Description: 基类activity
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BaseActivity";
    private Context mContext;
    protected P mPresenter;
    protected LodingDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (onCreatePresenter() != null){
            mPresenter = onCreatePresenter();
        }
        mContext = this;
        createDialog();
        onCreateView(savedInstanceState);
        initModule();
        initData();
    }

    private void createDialog() {
        if(mDialog == null){
            mDialog = new LodingDialog(this,"loading...");
        }
    }

    protected abstract void onCreateView(Bundle savedInstanceState);

    protected abstract void initModule();

    protected abstract void initData();
    protected abstract P onCreatePresenter();

    public void startActivity(Class<? extends AppCompatActivity> target,Bundle bundle, boolean finish){
        Intent intent = new Intent();
        intent.setClass(this,target);
        if(bundle != null){
            intent.putExtra(getPackageName(),bundle);
        }
        startActivity(intent);
        if(finish){
            finish();
        }

    }
    public void setBackBtn(String color){
        ImageView backBtn = findViewById(R.id.iv_back);
        backBtn.setVisibility(View.VISIBLE);
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(),R.drawable.shape_back,getTheme());
        //需要改变的颜色
        vectorDrawableCompat.setTint(Color.parseColor(color));
        backBtn.setImageDrawable(vectorDrawableCompat);
        backBtn.setOnClickListener(v -> {
            System.gc();
            onBackPressed();
        });
    }
    public void setLeftTitleText(int resId) {
        TextView leftTitle = findViewById(R.id.tv_left_title);
        leftTitle.setVisibility(View.VISIBLE);
        leftTitle.setText(resId);
    }
    public void setLeftTitleTextColorWhite() {
        TextView leftTitle = findViewById(R.id.tv_left_title);
        leftTitle.setTextColor(Color.parseColor("#ffffff"));
    }
    public void showDialog(){
        if(mDialog != null && !mDialog.isShowing()){
            mDialog.show();
        }
    }
    public void hideDialog(){
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
    @Override
    protected void onDestroy() {
        System.gc();
        if(mPresenter != null){
            mPresenter = null;
        }
        super.onDestroy();
    }
}
