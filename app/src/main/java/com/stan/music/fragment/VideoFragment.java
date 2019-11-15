package com.stan.music.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stan.music.App;
import com.stan.music.R;
import com.stan.music.base.BaseFragment;
import com.stan.music.base.BasePresenter;

/**
 * Author: Stan
 * Date: 2019/11/7 15:15
 * Description: ${DESCRIPTION}
 */
public class VideoFragment extends BaseFragment {
    public VideoFragment() {
        setFragmentTitle(App.getContext().getString(R.string.video));
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void initVariables(Bundle bundle) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine,container,false);

        return rootView;
    }

    @Override
    public void onClick(View v) {

    }
}
