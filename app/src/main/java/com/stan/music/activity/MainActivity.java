package com.stan.music.activity;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.stan.music.R;
import com.stan.music.adapter.MultiFragmentPagerAdapter;
import com.stan.music.base.BaseActivity;
import com.stan.music.base.BaseFragment;
import com.stan.music.base.BasePresenter;
import com.stan.music.bean.LoginBean;
import com.stan.music.fragment.FindFragment;
import com.stan.music.fragment.MineFragment;
import com.stan.music.fragment.VideoFragment;
import com.stan.music.fragment.VillageCloudFragment;
import com.stan.music.utils.ClickUtil;
import com.stan.music.utils.GsonUtil;
import com.stan.music.utils.SharePreferenceUtil;
import com.stan.music.widget.BottomSongPlayBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {
    //viewPager会缓存的Fragment数量
    private static final int VIEWPAGER_OFF_SCREEN_PAGE_LIMIT = 2;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_username)
    TextView userName;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.bottom_controller)
    BottomSongPlayBar bottomController;
    private long firstTime;

    private List<BaseFragment> fragments = new ArrayList<>();
    private MultiFragmentPagerAdapter mPagerAdapter;
    private LoginBean loginBean;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorTabBar)
                .statusBarDarkFont(false)
                .init();

        mPagerAdapter = new MultiFragmentPagerAdapter(getSupportFragmentManager());
        fragments.add(new MineFragment());
        fragments.add(new FindFragment());
        fragments.add(new VillageCloudFragment());
        fragments.add(new VideoFragment());
        mPagerAdapter.init(fragments);
    }

    @Override
    protected void initModule() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        String userLoginInfo = SharePreferenceUtil.getInstance(this).getUserInfo("");
        loginBean = GsonUtil.formJson(userLoginInfo,LoginBean.class);

        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(VIEWPAGER_OFF_SCREEN_PAGE_LIMIT);
        viewPager.setCurrentItem(1);
        mPagerAdapter.getItem(1).setUserVisibleHint(true);
        tabTitle.setupWithViewPager(viewPager);
        tabTitle.setTabTextColors(getResources().getColor(R.color.colorTextNormal),getResources().getColor(R.color.colorTextSelect));
        assert loginBean != null;
        initView(loginBean);
        setSelectTextBoldAndBig(Objects.requireNonNull(tabTitle.getTabAt(1)));
        initTabListener();
    }

    /**
     * 根据用户的LoginBean初始化一些信息
     * @param bean 用户信息
     */
    private void initView(LoginBean bean) {
        if (bean.getProfile().getAvatarUrl() != null) {
            String avatarUrl = bean.getProfile().getAvatarUrl();
            Glide.with(this).load(avatarUrl).into(ivAvatar);
        }
        if (bean.getProfile().getNickname() != null) {
            userName.setText(bean.getProfile().getNickname());
        }

    }

    private void initTabListener() {
        tabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setSelectTextBoldAndBig(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setSelectTextBoldAndBig(TabLayout.Tab tab) {
        TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.design_layout_tab_text,null);
        textView.setText(tab.getText());
        textView.setScaleX(1.3f);
        textView.setScaleY(1.5f);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setTextColor(getResources().getColor(R.color.colorTextSelect));
        tab.setCustomView(textView);
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    @OnClick({R.id.ic_nav})
    public void onClick(View v) {
        if(ClickUtil.isFastClick(1000,v)){
            return;
        }
        switch (v.getId()){
            case R.id.ic_nav:
                drawer.openDrawer(GravityCompat.START);
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(drawer.isDrawerOpen(GravityCompat.START)){
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            exitApp(2000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 退出应用
     * @param time 时间间隔
     */
    private void exitApp(int time) {
        if(System.currentTimeMillis() - firstTime > time){
            ToastUtils.show(R.string.press_exit_again);
            firstTime = System.currentTimeMillis();
        }else {
            finish();//销毁当前activity
            System.exit(0);
        }
    }
}
