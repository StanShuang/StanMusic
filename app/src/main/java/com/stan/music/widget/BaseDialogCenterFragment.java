package com.stan.music.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.stan.music.R;

/**
 * Author: Stan
 * Date: 2019/11/18 11:26
 * Description: ${DESCRIPTION}
 */
public abstract class BaseDialogCenterFragment extends DialogFragment{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置style
        setStyle(DialogFragment.STYLE_NORMAL, R.style.InputDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置dialog的宽高
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置背景颜色
        getDialog().getWindow().setBackgroundDrawable(null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去除标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getDialog().getWindow();
        assert window != null;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

        return onCreateView(inflater, container);
    }

    public abstract View onCreateView(LayoutInflater inflater,ViewGroup container);
}
