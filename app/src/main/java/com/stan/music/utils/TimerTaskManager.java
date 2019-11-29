package com.stan.music.utils;

import android.os.Handler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import io.reactivex.exceptions.Exceptions;

/**
 * Author: Stan
 * Date: 2019/11/25 10:37
 * Description: 时间管理工具
 */
public class TimerTaskManager {
    private static final long PROGRESS_UPDATE_INTERNAL = 1000;
    private static final long PROGRESS_UPDATE_INITIAL_INTERVAL = 100;
    private Runnable mUpdateProgressTask;
    private final ScheduledExecutorService mExectorService = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mScheduledFuture;
    private Handler mHandler = new Handler();

    /**
     * 开始更新进度条(同时更新歌词)
     */
    public void startUpdateProgress() {
        if (!mExectorService.isShutdown()) {
            mScheduledFuture = mExectorService.scheduleAtFixedRate(() -> {
                        if (mUpdateProgressTask != null) {
                            mHandler.post(mUpdateProgressTask);
                        }
                    },
                    PROGRESS_UPDATE_INITIAL_INTERVAL,
                    PROGRESS_UPDATE_INTERNAL,
                    TimeUnit.MILLISECONDS);
        }

    }


    /**
     * 设置定时的Runnable
     *
     * @param task
     */
    public void setUpdateProgressTask(Runnable task) {
        this.mUpdateProgressTask = task;
    }

    /**
     * 停止更新进度条
     */
    public void stopUpdateProgress(){
        if(mScheduledFuture != null){
            mScheduledFuture.cancel(false);
        }
    }

    /**
     * 释放资源
     */
    public void removeUpdateProgressTask(){
        stopUpdateProgress();
        mExectorService.shutdown();
        mHandler.removeCallbacksAndMessages(null);
    }
}
