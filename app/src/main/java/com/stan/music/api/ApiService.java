package com.stan.music.api;

import com.stan.music.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: Stan
 * Date: 2019/11/5 15:07
 * Description: ${DESCRIPTION}
 */
public interface ApiService {
    String BASE_URL = "http://192.168.1.196:3000/";

    @GET("login/cellphone")
    Observable<LoginBean> login(@Query("phone") String phone , @Query("password")String password);

}
