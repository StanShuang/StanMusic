package com.stan.music.activity.mvp.contract;

import com.stan.music.base.BaseModel;
import com.stan.music.base.BasePresenter;
import com.stan.music.base.BaseView;
import com.stan.music.bean.LoginBean;

import io.reactivex.Observable;

/**
 * Author: Stan
 * Date: 2019/11/4 17:56
 * Description: ${DESCRIPTION}
 */
public interface LoginContract {
    interface View extends BaseView{
        void onLoginSuccess(LoginBean bean);
        void onLoginFail(String e);
    }
    interface Model extends BaseModel{
        Observable<LoginBean> login(String phone,String password);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void login(String phone,String password);
    }
}
