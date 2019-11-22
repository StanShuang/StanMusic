package com.stan.music.api;

import com.stan.music.bean.LoginBean;
import com.stan.music.bean.LyricBean;
import com.stan.music.bean.PlaylistDetailBean;
import com.stan.music.bean.UserPlaylistBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author: Stan
 * Date: 2019/11/5 15:07
 * Description: ${DESCRIPTION}
 */
public interface ApiService {
    String BASE_URL = "http://192.168.1.84:3000/";

    @GET("login/cellphone")
    Observable<LoginBean> login(@Query("phone") String phone , @Query("password")String password);

    @GET("user/playlist")
    Observable<UserPlaylistBean> getUserPlaylist(@Query("uid")long uid);

    @GET("playlist/detail")
    Observable<PlaylistDetailBean> getPlaylistDetail(@Query("id") long id);

    @GET("lyric")
    Observable<LyricBean> getLyric(@Query("id") long id);


}
