package com.stan.music.activity.mvp.presenter;



import android.text.TextUtils;

import com.lzx.starrysky.provider.SongInfo;
import com.stan.music.activity.mvp.contract.PlayListContract;
import com.stan.music.activity.mvp.model.PlayListModel;
import com.stan.music.bean.PlaylistDetailBean;
import com.stan.music.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.stan.music.utils.ConstantUtil.SONG_URL;

/**
 * Author: Stan
 * Date: 2019/11/19 15:22
 * Description: ${DESCRIPTION}
 */
public class PlayListPresenter extends PlayListContract.Presenter {
    private static final String TAG = "PlayListPresenter";
    private List<SongInfo> songInfos = new ArrayList<>();

    public PlayListPresenter(PlayListContract.View view) {
        this.mView = view;
        mModel = new PlayListModel();
    }

    @Override
    public void getPlayListDetail(long id) {
        mModel.getPlayListDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlaylistDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.d(TAG, "getPlayListDetail onSubscribe");

                    }

                    @Override
                    public void onNext(PlaylistDetailBean bean) {
                        LogUtil.d(TAG, "PlaylistDetailBean : " + bean);
                        songInfos.clear();
                        List<PlaylistDetailBean.PlaylistBean.TracksBean> beanList = new ArrayList<>();
                        beanList.addAll(bean.getPlaylist().getTracks());
                        for (int i = 0; i < beanList.size(); i++) {
                            SongInfo beanInfo = new SongInfo();
                            beanInfo.setArtist(beanList.get(i).getAr().get(0).getName());
                            beanInfo.setSongName(beanList.get(i).getName());
                            if(!TextUtils.isEmpty(beanList.get(i).getAl().getPicUrl())){
                                beanInfo.setSongCover(beanList.get(i).getAl().getPicUrl());
                                beanInfo.setArtistKey(beanList.get(i).getAl().getPicUrl());
                            }
                            beanInfo.setSongId(String.valueOf(beanList.get(i).getId()));
                            beanInfo.setDuration(beanList.get(i).getDt());
                            beanInfo.setSongUrl(SONG_URL + beanList.get(i).getId() + ".mp3");
                            beanInfo.setArtistId(String.valueOf(beanList.get(i).getAr().get(0).getId()));
                            beanInfo.setAlbumName(beanList.get(i).getAl().getName());
                            if(beanList.get(i).getTns() != null && beanList.get(i).getTns().size() > 0){
                                beanInfo.setTas((String) beanList.get(i).getTns().get(0));
                            }else{
                                if(beanList.get(i).getAlia().size() > 0){
                                    beanInfo.setSubtitle((String) beanList.get(i).getAlia().get(0));
                                }
                            }
                            songInfos.add(beanInfo);
                        }
                        int shareCount = bean.getPlaylist().getShareCount();
                        int commentCount = bean.getPlaylist().getCommentCount();
                        String url = bean.getPlaylist().getCreator().getAvatarUrl();
                        mView.getPlayListDetailSuccess(songInfos,shareCount,commentCount,url);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "onError : " + e);
                        mView.getPlayListDetailFail(e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        LogUtil.d(TAG, "getPlaylistDetail  onComplete");
                    }
                });
    }
}
