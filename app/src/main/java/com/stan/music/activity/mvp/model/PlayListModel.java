package com.stan.music.activity.mvp.model;

import com.stan.music.activity.mvp.contract.PlayListContract;
import com.stan.music.api.ApiEngine;
import com.stan.music.bean.PlaylistDetailBean;

import io.reactivex.Observable;

/**
 * Author: Stan
 * Date: 2019/11/19 15:19
 * Description: ${DESCRIPTION}
 */
public class PlayListModel implements PlayListContract.Model {
    @Override
    public Observable<PlaylistDetailBean> getPlayListDetail(long id) {
        return ApiEngine.getInstance().getApiService().getPlaylistDetail(id);
    }
}
