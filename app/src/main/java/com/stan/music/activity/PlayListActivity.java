package com.stan.music.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzx.starrysky.provider.SongInfo;
import com.stan.music.R;
import com.stan.music.activity.mvp.contract.PlayListContract;
import com.stan.music.activity.mvp.presenter.PlayListPresenter;
import com.stan.music.adapter.SongListAdapter;
import com.stan.music.base.BaseActivity;
import com.stan.music.utils.BitmapUtil;
import com.stan.music.utils.DensityUtil;
import com.stan.music.utils.LogUtil;
import com.stan.music.utils.MathUtils;
import com.stan.music.widget.AppBarStateChangeListener;
import com.stan.music.widget.RoundRectView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.stan.music.utils.ConstantUtil.PLAYLIST_CREATOR_AVATARURL;
import static com.stan.music.utils.ConstantUtil.PLAYLIST_CREATOR_NICKNAME;
import static com.stan.music.utils.ConstantUtil.PLAYLIST_FIRST;
import static com.stan.music.utils.ConstantUtil.PLAYLIST_ID;
import static com.stan.music.utils.ConstantUtil.PLAYLIST_NAME;
import static com.stan.music.utils.ConstantUtil.PLAYLIST_PICURL;

public class PlayListActivity extends BaseActivity<PlayListPresenter> implements PlayListContract.View {
    private static final String TAG = "PlayListActivity";
    //计算完成后发送的Handler msg
    public static final int COMPLETED = 0;
    @BindView(R.id.rv_playlist_song)
    RecyclerView rvPlaylist;
    @BindView(R.id.iv_cover)
    RoundRectView ivCover;
    @BindView(R.id.tv_playlist_name)
    TextView tvListName;
    @BindView(R.id.tv_creator_name)
    TextView tvCreatorName;
    @BindView(R.id.iv_creator_avatar)
    CircleImageView ivCreatorAvatar;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.background)
    ImageView ivBg;
    @BindView(R.id.appbar)
    AppBarLayout appBar;
    @BindView(R.id.ll_play)
    RelativeLayout llPlay;
    @BindView(R.id.title)
    RelativeLayout tabTitle;
    @BindView(R.id.tv_all_num)
    TextView tvAllNum;
    private SongListAdapter adapter;
    private String playlistName;
    private String playlistPicUrl;
    private String creatorName;
    private long playlistId;
    private String creatorUrl;
    private boolean isMyLike;
    int deltaDistance;
    int minDistance;
    //list数据
    private List<SongInfo> songInfos = new ArrayList<>();

    private ObjectAnimator alphaAnimator;
    //appbar收缩时全透明
    private float alphaCollapsed = 0;
    //appbar展开时全不透明
    private float alphaExpanded = 1;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_play_list);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setViewAlpha();
    }

    @Override
    protected void initModule() {
        //获取Intent中的数据
        ButterKnife.bind(this);
        if (getIntent() != null) {
            playlistPicUrl = getIntent().getStringExtra(PLAYLIST_PICURL);
            playlistName = getIntent().getStringExtra(PLAYLIST_NAME);
            creatorName = getIntent().getStringExtra(PLAYLIST_CREATOR_NICKNAME);
            creatorUrl = getIntent().getStringExtra(PLAYLIST_CREATOR_AVATARURL);
            playlistId = getIntent().getLongExtra(PLAYLIST_ID, 0);
            isMyLike = getIntent().getBooleanExtra(PLAYLIST_FIRST, false);
        }

    }

    @Override
    protected void initData() {
        //列表适配器设置
        setAdapter();
        //获取列表数据
        showDialog();
        mPresenter.getPlayListDetail(playlistId);
        //toolbar 设置
        setBackBtn();
        //页面元素赋值
        setViewText();
        minDistance = DensityUtil.dp2px(PlayListActivity.this, 85);
        deltaDistance = DensityUtil.dp2px(getApplication().getApplicationContext(), 300) - minDistance;


    }

    private void setViewText() {
        if (isMyLike) {
            tvListName.setText("我喜欢的音乐");
        } else {
            tvListName.setText(playlistName);
        }

        tvCreatorName.setText(creatorName);
        calculateColors(playlistPicUrl);
        Glide.with(this).load(playlistPicUrl).into(ivCover);
        Glide.with(this).load(creatorUrl).into(ivCreatorAvatar);
//        Glide.with(this)
//                .load(playlistPicUrl)
//                .apply(RequestOptions.bitmapTransform(new BlurTransformation(10, 30)))
//                .into(ivCoverBg);


    }

    private void setBackBtn() {
        setLeftTitleTextColorWhite();
        setLeftTitleText(R.string.title_song_list);

        ImageView backBtn = findViewById(R.id.iv_back);
        backBtn.setVisibility(View.VISIBLE);
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(), R.drawable.shape_back, getTheme());
        //改变图片颜色
        vectorDrawableCompat.setTint(Color.parseColor("#FFFFFF"));
        backBtn.setImageDrawable(vectorDrawableCompat);
        backBtn.setOnClickListener(v -> {
            System.gc();
            onBackPressed();
        });
    }

    private void setAdapter() {
        adapter = new SongListAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvPlaylist.setLayoutManager(manager);
        rvPlaylist.setAdapter(adapter);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                ivBg.setBackground((Drawable) msg.obj);
                getAlphaAnimatorBg().start();
            }
        }
    };

    private void calculateColors(String url) {
        new Thread(() -> {
            Bitmap bitmap = BitmapUtil.calculateColorsBitmap(url);
            Message msg = Message.obtain();
            msg.what = COMPLETED;
            msg.obj = new BitmapDrawable(getResources(), bitmap);
            handler.sendMessage(msg);
        }).start();
    }

    @Override
    protected PlayListPresenter onCreatePresenter() {
        return new PlayListPresenter(this);
    }


    @Override
    public void getPlayListDetailSuccess(List<SongInfo> bean, int shareCount, int commentCount, String avatarUrl) {
        if (!TextUtils.isEmpty(creatorUrl)) {
            Glide.with(this).load(avatarUrl).into(ivCreatorAvatar);
        }
        hideDialog();
        songInfos.clear();
        songInfos.addAll(bean);
        adapter.notifyDataSetChanged(songInfos);
        if (shareCount != 0) {
            tvShare.setText(MathUtils.mathFormat(shareCount));
        }
        if (commentCount != 0) {
            tvComment.setText(MathUtils.mathFormat(commentCount));
        }
        tvAllNum.setText("共"+ bean.size() + "首");

    }

    @Override
    public void getPlayListDetailFail(String e) {
        hideDialog();
    }

    @Override
    @OnClick({R.id.iv_title_seach, R.id.iv_title_more})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_seach:
                //tab 搜索

                break;
            case R.id.iv_title_more:
                //tab 展示更多
                break;
            default:
                break;
        }
    }

    private ObjectAnimator getAlphaAnimatorBg() {
        if(alphaAnimator == null){
            alphaAnimator = ObjectAnimator.ofFloat(ivBg,"alpha",0f,0.8f);
            alphaAnimator.setDuration(1500);
            alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        return alphaAnimator;
    }
    private void setViewAlpha() {
        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            protected void onStateChanged(AppBarLayout appBarLayout, State state) {
                if(state == State.COLLAPSED){
                    setAlphaView(alphaCollapsed);
                }else if(state == State.EXPANDED){
                    setAlphaView(alphaExpanded);
                }
            }

            @Override
            protected void onOffsetChanged(AppBarLayout appBarLayout) {
                float alphaPercent =(float) (llPlay.getTop() - minDistance) / (float) deltaDistance;
                setAlphaView(alphaPercent);
            }
        });
    }
    private void setAlphaView(float alpha){
        ivCover.setAlpha(alpha);
        ivCreatorAvatar.setAlpha(alpha);
        tvListName.setAlpha(alpha);
        tvCreatorName.setAlpha(alpha);
    }

}
