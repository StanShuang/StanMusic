package com.stan.music.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: Stan
 * Date: 2019/11/7 15:12
 * Description: ${DESCRIPTION}
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    public String fragmentTitle;
    //view是否加载完成
    private boolean isPrepared;
    private boolean isFragmentVisible;
    //是否为第一次加载
    private boolean isFirstLoad = true;

    //强制刷新数据 但仍然要 visible & Prepared，采取reset数据的方式，所以要重新走initData
    private boolean forceLoad = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isPrepared = true;
        isFirstLoad = true;
        View view = initView(inflater,container,savedInstanceState);
        lazyLoad();
        return view;
    }

    /**
     * ViewPager联合使用
     * isVisibleToUser表示是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            onVisible();
        }else{
            onInVisible();
        }
    }
    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInVisible();
        }
    }
    protected void onInVisible() {
        isFragmentVisible = false;
    }

    protected void onVisible() {
        isFragmentVisible = true;
        lazyLoad();
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected void lazyLoad() {
        if( isPrepared() && isFragmentVisible()){
            if(forceLoad || isFirstLoad()){
                forceLoad = false;
                isFirstLoad = false;
                initData();
            }
        }
    }

    protected abstract void initData();

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void setFragmentTitle(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }

    public String getTitle() {
        return fragmentTitle;
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isPrepared = false;
    }

    public boolean isFirstLoad() {
        return isFirstLoad;
    }

    /**
     * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
     */
    public void setForceLoad(boolean forceLoad) {
        this.forceLoad = forceLoad;
    }

    public boolean isFragmentVisible() {
        return isFragmentVisible;
    }
}
