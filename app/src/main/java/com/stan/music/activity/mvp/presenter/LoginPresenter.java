package com.stan.music.activity.mvp.presenter;

import com.stan.music.activity.mvp.contract.LoginContract;
import com.stan.music.activity.mvp.model.LoginModel;
import com.stan.music.bean.LoginBean;
import com.stan.music.utils.LogUtil;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Stan
 * Date: 2019/11/4 17:58
 * Description: ${DESCRIPTION}
 */
public class LoginPresenter extends LoginContract.Presenter {
    private final static String TAG = "LoginPresenter";

    public LoginPresenter(LoginContract.View view) {
        this.mView = view;
        this.mModel = new LoginModel();
    }

    @Override
    public void login(String phone, String password) {
        LogUtil.d(TAG, "login");
        mModel.login(phone,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(LoginBean bean) {
                        LogUtil.d(TAG, "onNext : " + bean);
                        mView.onLoginSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "onError : " + e.toString());
                        mView.onLoginFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d(TAG, "onComplete!");
                    }
                });

    }
}
