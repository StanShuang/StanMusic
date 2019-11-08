package com.stan.music.activity.mvp.model;

import com.stan.music.activity.mvp.contract.LoginContract;
import com.stan.music.api.ApiEngine;
import com.stan.music.bean.LoginBean;

import io.reactivex.Observable;

/**
 * Author: Stan
 * Date: 2019/11/4 17:58
 * Description: ${DESCRIPTION}
 */
public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<LoginBean> login(String phone, String password) {
        return ApiEngine.getInstance().getApiService().login(phone,password);
    }
}
