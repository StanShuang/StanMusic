package com.stan.music.api;

import com.stan.music.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Author: Stan
 * Date: 2019/11/5 15:36
 * Description: 返回结果拦截器
 */
public class ResponseInterceptor implements Interceptor {
    private static final String TAG = "ResponseInterceptor";
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        if (response.code() != 200) {
            return response;
        }
        long contentLength = responseBody.contentLength();
        if(!bodyEncoded(response.headers())){
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    return response;
                }
            }

            if (contentLength != 0) {
                String result = buffer.clone().readString(charset);
                LogUtil.d(TAG, " response.url():" + response.request().url());
                LogUtil.d(TAG, " response.body():" + result);
            }

        }


        return response;
    }
    private boolean bodyEncoded(Headers headers){
        String contentEncoded = headers.get("Content-Encoding");
        return contentEncoded != null && !contentEncoded.equalsIgnoreCase("identity");
    }
}
