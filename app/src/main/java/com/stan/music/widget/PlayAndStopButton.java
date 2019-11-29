package com.stan.music.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.stan.music.R;
import com.stan.music.manager.SongPlayManager;
import com.stan.music.utils.LogUtil;

/**
 * Author: Stan
 * Date: 2019/11/29 15:00
 * Description: 自定义播放按钮
 */
public class PlayAndStopButton extends View {
    private final static String TAG = "PlayAndStopButton";
    //画笔
    private Paint paintA, paintB,paintC;
    //中心坐标
    private int viewCenterX, viewCeneterY;
    /**
     * 有效长度的一般（View长宽较小者的一半）
     */
    private int viewHalfLength;
    /**
     * 包围最外侧圆环的矩形
     */
    private RectF rectF = new RectF();
    /**
     * 进度
     */
    private int progress = 0;
    /** 三角形的三条边路径 */
    private Path path = new Path();
    private Point pointPauseA,pointPauseB,pointPauseC;
    private Point pointPlayA,pointPlayB;
    /**
     * 状态监听器
     */
    private OnStatusChangeListener onStatusChangeListener;

    public PlayAndStopButton(Context context) {
        this(context, null);
    }

    public PlayAndStopButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayAndStopButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paintA = new Paint();
        paintA.setColor(getResources().getColor(R.color.colorTabBar));
        paintA.setAntiAlias(true);
        paintA.setDither(true);

        paintB = new Paint();
        paintB.setAntiAlias(true);
        paintB.setDither(true);

        paintC = new Paint();
        paintC.setAntiAlias(true);
        paintC.setDither(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getWidthAndHeight();
    }

    /**
     * 得到视图等的高度宽度尺寸数据
     */
    private void getWidthAndHeight() {
        int viewWidth = getMeasuredWidth();
        int viewHeight = getMeasuredHeight();
        viewCenterX = viewWidth / 2;
        viewCeneterY = viewHeight / 2;

        viewHalfLength = viewWidth < viewHeight ? viewWidth / 2 : viewHeight / 2;

        int paintWidth = viewHalfLength / 12;
        rectF.left = viewCenterX - (viewHalfLength - paintWidth / 2);
        rectF.top = viewCeneterY - (viewHalfLength - paintWidth / 2);
        rectF.right = viewCenterX + (viewHalfLength - paintWidth / 2);
        rectF.bottom = viewCeneterY + (viewHalfLength - paintWidth / 2);

        pointPauseA = new Point(viewCenterX + viewHalfLength/2,viewCeneterY);
        double sin = Math.sin(Math.toRadians(60));
        double cos = Math.cos(Math.toRadians(60));
        pointPauseB = new Point(((int)((viewCenterX - cos * viewHalfLength) + viewCenterX) / 2),(int)((viewCeneterY - sin * viewHalfLength + viewCeneterY) / 2));
        pointPauseC = new Point(((int)((viewCenterX - cos * viewHalfLength) + viewCenterX) / 2),(int)((viewCeneterY + sin * viewHalfLength + viewCeneterY) / 2));
        pointPlayA = new Point(((int)((viewCenterX + cos * viewHalfLength) + viewCenterX) / 2),(int)((viewCeneterY - sin * viewHalfLength + viewCeneterY) / 2));
        pointPlayB = new Point(((int)((viewCenterX + cos * viewHalfLength) + viewCenterX) / 2),(int)((viewCeneterY + sin * viewHalfLength + viewCeneterY) / 2));


        paintA.setStrokeWidth(paintWidth);
        paintA.setStyle(Paint.Style.STROKE);
        paintB.setStrokeWidth(paintWidth);
        paintB.setStyle(Paint.Style.STROKE);
        paintC.setStrokeWidth(3);
        paintC.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//         画整体进度的圆环
        if(SongPlayManager.getInstance().isPlaying()){
            paintB.setColor(getResources().getColor(R.color.colorGray));
            LogUtil.d(TAG,"isPlaying");
        }else{
            paintB.setColor(getResources().getColor(R.color.colorTextPause));
            LogUtil.d(TAG,"isPause");
        }
        canvas.drawArc(rectF, 0, 360, false, paintB);
        //画进度条
        canvas.drawArc(rectF, -90, progress * 0.36f, false, paintA);
        if(SongPlayManager.getInstance().isPlaying()){
            paintC.setColor(getResources().getColor(R.color.colorTabBar));
            path.reset();
            path.moveTo(pointPauseB.x, pointPauseB.y);
            path.lineTo(pointPauseC.x, pointPauseC.y);
            path.moveTo(pointPlayA.x, pointPlayA.y);
            path.lineTo(pointPlayB.x, pointPlayB.y);
            path.close();
            canvas.drawPath(path, paintC);
        }else{
            paintC.setColor(getResources().getColor(R.color.colorTextPause));
            path.reset();
            path.moveTo(pointPauseA.x, pointPauseA.y);
            path.lineTo(pointPauseB.x, pointPauseB.y);
            path.lineTo(pointPauseC.x, pointPauseC.y);
            path.close();
            canvas.drawPath(path, paintC);
        }


    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if (SongPlayManager.getInstance().isPlaying()) {
//                //需暂停
//                onStatusChangeListener.pause();
//            } else if (SongPlayManager.getInstance().isPaused() || SongPlayManager.getInstance().isIdle()) {
//                //需播放
//                onStatusChangeListener.play();
//            }
//
//        }
//        return super.onTouchEvent(event);
//    }

    /**
     * 播放暂停状态监听的接口
     */
    public interface OnStatusChangeListener {

        void play();

        void pause();
    }

    /**
     * 设置监听接口
     */
    public void setOnStatusChangeListener(OnStatusChangeListener onStatusChangeListener) {
        this.onStatusChangeListener = onStatusChangeListener;
    }
}
