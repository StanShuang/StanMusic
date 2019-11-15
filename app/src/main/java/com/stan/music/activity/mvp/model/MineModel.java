package com.stan.music.activity.mvp.model;

import com.stan.music.activity.mvp.contract.MineContract;
import com.stan.music.api.ApiEngine;
import com.stan.music.bean.UserPlaylistBean;

import io.reactivex.Observable;

/**
 * Author: Stan
 * Date: 2019/11/11 11:28
 * Description: ${DESCRIPTION}
 */
public class MineModel implements MineContract.Model {
    @Override
    public Observable<UserPlaylistBean> getUserPlaylist(long uid) {
        return ApiEngine.getInstance().getApiService().getUserPlaylist(uid);
    }
}
