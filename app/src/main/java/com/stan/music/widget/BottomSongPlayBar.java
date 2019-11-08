package com.stan.music.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.stan.music.R;

/**
 * Author: Stan
 * Date: 2019/11/6 14:29
 * Description: ${DESCRIPTION}
 */
public class BottomSongPlayBar extends ConstraintLayout {
    private Context mContext;
    private ConstraintLayout rlSongController;
    public BottomSongPlayBar(Context context) {
        this(context,null);
    }

    public BottomSongPlayBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomSongPlayBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        rlSongController = (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_bottom_songplay_control,this,true);
    }
}
