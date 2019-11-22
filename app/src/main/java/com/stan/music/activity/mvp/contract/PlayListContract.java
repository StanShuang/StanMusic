package com.stan.music.activity.mvp.contract;

import com.lzx.starrysky.provider.SongInfo;
import com.stan.music.base.BaseModel;
import com.stan.music.base.BasePresenter;
import com.stan.music.base.BaseView;
import com.stan.music.bean.PlaylistDetailBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Author: Stan
 * Date: 2019/11/19 15:12
 * Description: ${DESCRIPTION}
 */
public interface PlayListContract {
    interface View extends BaseView {
        void getPlayListDetailSuccess(List<SongInfo> bean, int num1, int num2, String url);
        void getPlayListDetailFail(String e);

    }

    interface Model extends BaseModel{
        Observable<PlaylistDetailBean> getPlayListDetail(long id);
    }

    abstract class Presenter extends BasePresenter<View,Model>{
        //获取对应歌单列表
        public abstract void getPlayListDetail(long id);
    }
}
