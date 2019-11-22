package com.stan.music.activity.mvp;

import com.stan.music.api.ApiEngine;
import com.stan.music.bean.LyricBean;
import com.stan.music.listener.LyricsListeners;
import com.stan.music.utils.LogUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Stan
 * Date: 2019/11/22 15:38
 * Description: 获取歌词的控制器
 */
public class LyricsPresenter {
    private static final String TAG = "LyricsPresenter";
    private LyricsListeners listeners;
    public void setLyricsListeners(LyricsListeners listeners){
        this.listeners = listeners;
    }
    public void getLyrics(long id){
        ApiEngine.getInstance().getApiService().getLyric(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LyricBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LyricBean bean) {
                        LogUtil.d(TAG, "getLyric onNext" );
                        listeners.getLyricsSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "getLyric onError" + e.getLocalizedMessage());
                        listeners.getLyricsFail(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
