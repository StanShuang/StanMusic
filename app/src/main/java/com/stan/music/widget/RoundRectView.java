package com.stan.music.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.stan.music.R;

/**
 * Author: Stan
 * Date: 2019/11/12 14:13
 * Description: 歌单封面的view,是一个圆角矩形
 */
public class RoundRectView extends AppCompatImageView {
    private float roundRatio = 16f  ;
    private Path path;
    public RoundRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundRectView,0,0);
        try{
            roundRatio = array.getFloat(R.styleable.RoundRectView_roundRatio,16f);
        }finally {
            array.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (path == null) {
            path = new Path();
            path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), roundRatio * getWidth(), roundRatio * getHeight(), Path.Direction.CW);
        }
        canvas.save();
        canvas.clipPath(path);
        super.onDraw(canvas);
        canvas.restore();

    }
}
