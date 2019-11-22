package com.stan.music.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.stan.music.App;
import com.stan.music.R;
import com.stan.music.activity.PlayListActivity;
import com.stan.music.activity.mvp.contract.MineContract;
import com.stan.music.activity.mvp.presenter.MinePresenter;
import com.stan.music.adapter.UserPlaylistAdapter;
import com.stan.music.base.BaseFragment;
import com.stan.music.bean.LoginBean;
import com.stan.music.bean.PlayListItemBean;
import com.stan.music.bean.UserPlaylistBean;
import com.stan.music.utils.ConstantUtil;
import com.stan.music.utils.GsonUtil;
import com.stan.music.utils.LogUtil;
import com.stan.music.utils.SharePreferenceUtil;
import com.stan.music.widget.AddDialogFragment;
import com.stan.music.widget.MineMoreDialogFragment;
import com.stan.music.widget.MoreDialogFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.stan.music.utils.ConstantUtil.PLAYLIST_CREATOR_AVATARURL;
import static com.stan.music.utils.ConstantUtil.PLAYLIST_CREATOR_NICKNAME;
import static com.stan.music.utils.ConstantUtil.PLAYLIST_FIRST;
import static com.stan.music.utils.ConstantUtil.PLAYLIST_ID;
import static com.stan.music.utils.ConstantUtil.PLAYLIST_NAME;

/**
 * Author: Stan
 * Date: 2019/11/7 15:15
 * Description: ${DESCRIPTION}
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {

    private static final String TAG = "MineFragment";
    @BindView(R.id.rv_mine_playlist)
    RecyclerView rvPlayList;
    @BindView(R.id.rv_collection_playlist)
    RecyclerView rvCollectionList;
    @BindView(R.id.tv_create_count)
    TextView tvCreateCount;
    @BindView(R.id.tv_collection_count)
    TextView tvCollectionCount;
    @BindView(R.id.iv_icon_below)
    ImageView ivIconBelow;
    @BindView(R.id.iv_icon_below_collection)
    ImageView ivIconBelowCollection;
    private boolean isVisPlay = false;//创建歌单是否显示
    private boolean isVisCollection = false;//收藏歌单是否显示
    private LoginBean loginBean;
    private long uid;

    public MineFragment() {
        setFragmentTitle(App.getContext().getString(R.string.mine));
    }

    private UserPlaylistAdapter adapterMine;
    private UserPlaylistAdapter adapterCollection;
    private List<UserPlaylistBean.PlaylistBean> playlistBeans = new ArrayList<>();
    private List<PlayListItemBean> adapterMineList = new ArrayList<>();
    private List<PlayListItemBean> adapterCollectionList = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected MinePresenter onCreatePresenter() {
        return new MinePresenter(this);
    }

    @Override
    protected void initVariables(Bundle bundle) {

    }

    @Override
    protected void initData() {
        //个人信息总和列表
        if (!SharePreferenceUtil.getInstance(getContext()).getUserInfo("").isEmpty()) {
            loginBean = GsonUtil.formJson(SharePreferenceUtil.getInstance(getContext()).getUserInfo(""), LoginBean.class);
            uid = loginBean.getAccount().getId();
            //创建的歌单的适配器
            setAdapterMine();
            //收藏歌单的适配器
            setAdapterCollect();
            showDialog();
            presenter.getUserPlaylist(uid);
        }


    }

    private void setAdapterCollect() {
        adapterMine = new UserPlaylistAdapter(getContext());
        rvPlayList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPlayList.setAdapter(adapterMine);
        adapterMine.setListener(listener);
        adapterMine.setShowSmartPlay(true);
    }

    private void setAdapterMine() {
        adapterCollection = new UserPlaylistAdapter(getContext());
        rvCollectionList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCollectionList.setAdapter(adapterCollection);
        adapterCollection.setListener(listener);
        adapterCollection.setShowSmartPlay(false);
        LogUtil.d(TAG, loginBean.getAccount().getUserName());
    }

    private UserPlaylistAdapter.OnPlayListItemClickListener listener = new UserPlaylistAdapter.OnPlayListItemClickListener() {
        @Override
        public void onPlayListItemClick(int position,boolean isShowSmartPlay) {
            //列表点击
            if(isShowSmartPlay){
                startViewActivity(adapterMineList,position);
            }else{
                startViewActivity(adapterCollectionList,position);
            }
        }

        @Override
        public void onSmartPlayClick(int position) {
            //心动模式
        }

        @Override
        public void onShowMoreDialog(int position, boolean isShowSmartPlay) {
            //弹出更多操作弹窗
            if (isShowSmartPlay) {//创建的歌单
                MoreDialogFragment fragment = (MoreDialogFragment) MoreDialogFragment.getInstance(true, adapterMineList.get(position).getPlayListName());
                fragment.show(getFragmentManager(), "dialog");

            } else {//收藏的歌单
                MoreDialogFragment fragment = (MoreDialogFragment) MoreDialogFragment.getInstance(false, adapterCollectionList.get(position).getPlayListName());
                fragment.show(getFragmentManager(), "dialog");
            }

        }
    };

    private void startViewActivity(List<PlayListItemBean> list,int position) {
        Intent intent = new Intent(getContext(), PlayListActivity.class);
        intent.putExtra(ConstantUtil.PLAYLIST_PICURL, list.get(position).getCoverUrl());
        intent.putExtra(PLAYLIST_NAME, list.get(position).getPlayListName());
        intent.putExtra(PLAYLIST_CREATOR_NICKNAME, list.get(position).getPlaylistCreator());
        intent.putExtra(PLAYLIST_CREATOR_AVATARURL, list.get(position).getAvatarUrl());
        intent.putExtra(PLAYLIST_ID, list.get(position).getPlaylistId());
        if(position == 0){
            intent.putExtra(PLAYLIST_FIRST,true);
        }
        getContext().startActivity(intent);

    }

    @Override
    @OnClick({R.id.local_music, R.id.recent_play, R.id.download_manager,
            R.id.my_radio, R.id.my_collection, R.id.rl_create_playlist, R.id.rl_collection_playlist,
            R.id.iv_more, R.id.iv_more_collection, R.id.iv_new})
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
                if (isVisPlay) {
                    setClockwiseAnimation(R.id.rl_create_playlist);
                } else {
                    setCounterclockwiseAnimation(R.id.rl_create_playlist);
                }

                break;
            case R.id.rl_collection_playlist:
                //开启收藏个歌单的显示隐藏(动画)
                if (isVisCollection) {
                    setClockwiseAnimation(R.id.rl_collection_playlist);
                } else {
                    setCounterclockwiseAnimation(R.id.rl_collection_playlist);
                }

                break;
            case R.id.iv_more:
                MineMoreDialogFragment fragment = (MineMoreDialogFragment) MineMoreDialogFragment.getInstance(true);
                fragment.show(getFragmentManager(), "dialog");
                break;
            case R.id.iv_more_collection:
                MineMoreDialogFragment fragmentCollection = (MineMoreDialogFragment) MineMoreDialogFragment.getInstance(false);
                fragmentCollection.show(getFragmentManager(), "dialog");
                break;
            case R.id.iv_new:
                //创建新的歌单
                AddDialogFragment addFragment = new AddDialogFragment();
                addFragment.show(getFragmentManager(), "dialog");
                addFragment.setOnDialogClickListener(dialogListener);
                break;

            default:
                break;
        }
    }

    /**
     * 获取
     *
     * @param bean
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onGetUserPlaylistSuccess(UserPlaylistBean bean) {
        hideDialog();
        LogUtil.d(TAG, "onGetUserPlaylistSuccess : ");
        playlistBeans.clear();
        adapterMineList.clear();
        adapterCollectionList.clear();
        playlistBeans.addAll(bean.getPlaylist());

        for (int i = 0; i < playlistBeans.size(); i++) {
            PlayListItemBean beanInfo = new PlayListItemBean();
            beanInfo.setCoverUrl(playlistBeans.get(i).getCoverImgUrl());
            beanInfo.setPlaylistId(playlistBeans.get(i).getId());
            beanInfo.setPlayCount(playlistBeans.get(i).getPlayCount());
            beanInfo.setPlayListName(playlistBeans.get(i).getName());
            beanInfo.setSongNumber(playlistBeans.get(i).getTrackCount());
            beanInfo.setPlaylistCreator(playlistBeans.get(i).getCreator().getNickname());
            beanInfo.setDefaultAvatar(playlistBeans.get(i).getCreator().isDefaultAvatar());
            beanInfo.setAvatarUrl(playlistBeans.get(i).getCreator().getAvatarUrl());
            if (playlistBeans.get(i).getCreator().isDefaultAvatar()) {
                adapterMineList.add(beanInfo);
            } else {
                adapterCollectionList.add(beanInfo);
            }


        }
        adapterMine.notifyDataSetChanged(adapterMineList);
        adapterCollection.notifyDataSetChanged(adapterCollectionList);
        tvCreateCount.setText("(" + adapterMineList.size() + ")");
        tvCollectionCount.setText("(" + adapterCollectionList.size() + ")");
    }

    @Override
    public void onGetUserPlaylistFail(String e) {
        hideDialog();
        LogUtil.d(TAG, "onGetUserPlaylistFail : " + e);
        ToastUtils.show(e);

    }

    private AddDialogFragment.OnDialogClickListener dialogListener = (title, isCheck) -> ToastUtils.show("实现添加歌单...");

    /**
     * 逆时针旋转
     */
    private void setCounterclockwiseAnimation(int id) {
        Animation animation = new RotateAnimation(0, -90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(400);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setViewInList(id);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (id == R.id.rl_create_playlist) {
            ivIconBelow.startAnimation(animation);
        } else {
            ivIconBelowCollection.startAnimation(animation);
        }


    }

    /**
     * 顺时针旋转
     */
    private void setClockwiseAnimation(int id) {
        Animation animation = new RotateAnimation(-90f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(400);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setViewInList(id);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (id == R.id.rl_create_playlist) {
            ivIconBelow.startAnimation(animation);
        } else {
            ivIconBelowCollection.startAnimation(animation);
        }
    }

    private void setViewInList(int id) {
        if (id == R.id.rl_create_playlist) {
            if (isVisPlay) {
                rvPlayList.setVisibility(View.VISIBLE);
                isVisPlay = false;
            } else {
                rvPlayList.setVisibility(View.GONE);
                isVisPlay = true;
            }
        } else if (id == R.id.rl_collection_playlist) {
            if (isVisCollection) {
                rvCollectionList.setVisibility(View.VISIBLE);
                isVisCollection = false;
            } else {
                rvCollectionList.setVisibility(View.GONE);
                isVisCollection = true;
            }
        }

    }
}
