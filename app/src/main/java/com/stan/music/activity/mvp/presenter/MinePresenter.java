package com.stan.music.activity.mvp.presenter;

import com.stan.music.activity.mvp.contract.MineContract;
import com.stan.music.activity.mvp.model.MineModel;
import com.stan.music.bean.UserPlaylistBean;
import com.stan.music.utils.LogUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Stan
 * Date: 2019/11/11 11:29
 * Description: ${DESCRIPTION}
 */
public class MinePresenter extends MineContract.Presenter {
    private final static String TAG = "MinePresenter";

    public MinePresenter(MineContract.View view) {
        this.mView = view;
        mModel = new MineModel();
    }

    @Override
    public void getUserPlaylist(long uid) {
        mModel.getUserPlaylist(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserPlaylistBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.d(TAG, "getUserPlaylist  onSubscribe");
                    }

                    @Override
                    public void onNext(UserPlaylistBean bean) {
                        LogUtil.d(TAG, "getUserPlaylist  onNext : " + bean);
                        mView.onGetUserPlaylistSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "getUserPlaylist  onError : " + e.toString());
                        mView.onGetUserPlaylistFail(e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d(TAG, "getUserPlaylist  onComplete");

                    }
                });
    }
}
