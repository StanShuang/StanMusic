package com.stan.music.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.stan.music.utils.LogUtil;

/**
 * Author: Stan
 * Date: 2019/11/20 17:37
 * Description: ${DESCRIPTION}
 */
public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {
    private static final String TAG = "AppBarStateChangeListener";
    public enum State{
        EXPANDED,//展开
        COLLAPSED,//收缩
        IDLE //其他
    }
    private State mCurrentState = State.IDLE;

    /**
     *
     * @param appBarLayout
     * @param verticalOffset 1.当verticalOffset==0的时候我们可以判断为展开状态;
     * 2.当Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()的时候我们可以判断为收缩状态
     *
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        onOffsetChanged(appBarLayout);
        if(verticalOffset == 0){
            if(mCurrentState != State.EXPANDED){
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        }else if(Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
            if(mCurrentState != State.COLLAPSED){
                onStateChanged(appBarLayout,State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        }else{
            if(mCurrentState != State.IDLE){
                onStateChanged(appBarLayout,State.IDLE);
            }
            mCurrentState = State.IDLE;
        }
    }


    protected abstract void onStateChanged(AppBarLayout appBarLayout, State state);

    protected abstract void onOffsetChanged(AppBarLayout appBarLayout);
}
