package com.stan.music.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjq.toast.ToastUtils;
import com.lzx.starrysky.provider.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.stan.music.App;
import com.stan.music.R;
import com.stan.music.activity.mvp.LyricsPresenter;
import com.stan.music.bean.LyricBean;
import com.stan.music.listener.LyricsListeners;
import com.stan.music.manager.SongPlayManager;
import com.stan.music.manager.event.MusicPauseEvent;
import com.stan.music.manager.event.MusicStartEvent;
import com.stan.music.utils.GsonUtil;
import com.stan.music.utils.LogUtil;
import com.stan.music.utils.SharePreferenceUtil;
import com.stan.music.utils.TimeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Hashtable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: Stan
 * Date: 2019/11/6 14:29
 * Description: ${DESCRIPTION}
 */
public class BottomSongPlayBar extends ConstraintLayout {
    private static final String TAG = "BottomSOogPlayBar";
    private Context mContext;
    private ConstraintLayout rlSongController;
    private CircleImageView ivCover;
    private ImageView ivPlay, ivController;
    private TextView tvSongName, tvSongSinger;
    private SongInfo currentSongInfo;
    private LyricsPresenter presenter;
    public BottomSongPlayBar(Context context) {
        this(context,null);
    }

    public BottomSongPlayBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BottomSongPlayBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        EventBus.getDefault().register(this);
        mContext = context;
        initView();
        initListener();
        initSongInfo();
        presenter = new LyricsPresenter();
        presenter.setLyricsListeners(lyricsListeners);
    }

    private void initSongInfo() {
        ////初始化的时候，要从本地拿最近一次听的歌曲
        currentSongInfo = SharePreferenceUtil.getInstance(App.getContext()).getLatestSong();
        if(currentSongInfo != null){
            setSongBean(currentSongInfo);
            LogUtil.d(TAG,"isPlaying: " + SongPlayManager.getInstance().isPlaying());
            if(SongPlayManager.getInstance().isPlaying()){
                ivPlay.setImageResource(R.drawable.shape_stop);
            }
        }
    }

    private void initListener() {
        ivPlay.setOnClickListener( v -> {
            if(!SongPlayManager.getInstance().isPlaying()){
                SongPlayManager.getInstance().clickBottomControllerPlay(currentSongInfo);
            }else{
                SongPlayManager.getInstance().pauseMusic();
            }
        });


    }

    private void initView() {
        rlSongController = (ConstraintLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_bottom_songplay_control,this,true);
        ivCover = rlSongController.findViewById(R.id.iv_cover);
        ivPlay = rlSongController.findViewById(R.id.iv_bottom_play);
        ivController = rlSongController.findViewById(R.id.iv_bottom_controller);
        tvSongName = rlSongController.findViewById(R.id.tv_songname);
        tvSongSinger = rlSongController.findViewById(R.id.tv_singer);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playStartMusicEvent(MusicStartEvent event){
        LogUtil.d(TAG, "MusicStartEvent :" + event);
        setSongBean(event.getSongInfo());
        ivPlay.setImageResource(R.drawable.shape_stop);
        presenter.getLyrics(Long.parseLong(event.getSongInfo().getSongId()));
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPauseMusicEvent(MusicPauseEvent event){
        LogUtil.d(TAG, "onPauseMusicEvent :");
        ivPlay.setImageResource(R.drawable.shape_play);
    }
    private void setSongBean(SongInfo bean) {
        currentSongInfo = bean;
        tvSongName.setText(bean.getSongName());
        tvSongSinger.setText(bean.getArtist());
        if (!TextUtils.isEmpty(bean.getSongCover())) {
            Glide.with(mContext).load(bean.getSongCover()).into(ivCover);
        }

    }
    private LyricsListeners lyricsListeners = new LyricsListeners() {
        @Override
        public void getLyricsSuccess(LyricBean bean) {
            LogUtil.d(TAG,"getLyricsSuccess"+bean.getLrc().getLyric());
            String str = bean.getLrc().getLyric();
            Hashtable<String,String> tab = GsonUtil.getLyricsAndTime(str);
           long time = SongPlayManager.getInstance().getPlayingPosition();
            new Thread(() -> {
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                LogUtil.d(TAG,TimeUtil.getTimeNoYMDH(time+1000)+"--------");

            }).start();
        }

        @Override
        public void getLyricsFail(String e) {
            ToastUtils.show(e);
        }
    };
}
