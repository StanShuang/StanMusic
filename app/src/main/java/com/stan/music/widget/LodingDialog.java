package com.stan.music.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.stan.music.R;

/**
 * Author: Stan
 * Date: 2019/11/6 10:46
 * Description: ${DESCRIPTION}
 */
public class LodingDialog extends Dialog {
    private static final String TAG = "LoadingDialog";
    private String msg;

    public LodingDialog(@NonNull Context context,String msg) {
        super(context);
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_load_dialog);
        setDialogCancelable(false);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        new Handler().postDelayed(() -> setDialogCancelable(true),3000);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setDialogCancelable(false);

    }

    private void setDialogCancelable(boolean cancel) {
        setCancelable(cancel);
        setCanceledOnTouchOutside(cancel);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
