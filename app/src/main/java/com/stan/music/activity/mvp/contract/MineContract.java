package com.stan.music.activity.mvp.contract;

import com.stan.music.base.BaseModel;
import com.stan.music.base.BasePresenter;
import com.stan.music.base.BaseView;
import com.stan.music.bean.UserPlaylistBean;

import io.reactivex.Observable;

/**
 * Author: Stan
 * Date: 2019/11/11 11:28
 * Description: ${DESCRIPTION}
 */
public interface MineContract {
    interface View extends BaseView{
        void onGetUserPlaylistSuccess(UserPlaylistBean bean);
        void onGetUserPlaylistFail(String e);
    }
    interface Model extends BaseModel{
        Observable<UserPlaylistBean> getUserPlaylist(long uid);
    }

    abstract class Presenter extends BasePresenter<MineContract.View,MineContract.Model>{
        public abstract void getUserPlaylist(long uid);
    }
}
