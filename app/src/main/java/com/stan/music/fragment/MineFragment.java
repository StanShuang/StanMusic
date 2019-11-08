package com.stan.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stan.music.App;
import com.stan.music.R;
import com.stan.music.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: Stan
 * Date: 2019/11/7 15:15
 * Description: ${DESCRIPTION}
 */
public class MineFragment extends BaseFragment {
    public MineFragment() {
        setFragmentTitle(App.getContext().getString(R.string.mine));
    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        //个人信息总和列表

    }

    @Override
    @OnClick({R.id.local_music, R.id.recent_play, R.id.download_manager, R.id.my_radio, R.id.my_collection, R.id.rl_create_playlist, R.id.rl_collection_playlist})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.local_music:
                //本地音乐

                break;
            case R.id.recent_play:
                //最近播放

                break;
            case R.id.download_manager:
                //下载管理
                break;
            case R.id.my_radio:
                //我的电台

                break;
            case R.id.my_collection:
                //我的收藏

                break;
            case R.id.rl_create_playlist:
                //开启创建个歌单的显示隐藏(动画)

                break;
            case R.id.rl_collection_playlist:
                //开启收藏个歌单的显示隐藏(动画)

                break;
            default:
                break;
        }
    }
}
