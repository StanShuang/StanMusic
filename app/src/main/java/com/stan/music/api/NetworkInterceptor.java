package com.stan.music.api;

import com.stan.music.utils.NetUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Stan
 * Date: 2019/11/5 15:23
 * Description: 网络拦截器
 */
public class NetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //无网络时强制使用缓存
        if(!NetUtil.isConnected()){
            request =request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        if(NetUtil.isConnected()){
            //有网络时，设置超时为0
            int maxStale = 0;
            response.newBuilder().header("Cache-Control","public,max-age=" + maxStale);
        }else{
            //无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            response.newBuilder().header("Cache-Control","public, only-if-cache, max-age=" + maxStale);
        }
        return response;
    }
}
