package com.stan.music.manager;

import android.util.Log;

import com.hjq.toast.ToastUtils;
import com.lzx.starrysky.MusicManager;
import com.lzx.starrysky.StarrySky;
import com.lzx.starrysky.control.OnPlayerEventListener;
import com.lzx.starrysky.provider.SongInfo;
import com.stan.music.App;
import com.stan.music.api.ApiService;
import com.stan.music.bean.MusicCanPlayBean;
import com.stan.music.manager.event.MusicPauseEvent;
import com.stan.music.manager.event.MusicStartEvent;
import com.stan.music.utils.GsonUtil;
import com.stan.music.utils.LogUtil;
import com.stan.music.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Author: Stan
 * Date: 2019/11/21 14:17
 * Description: 播放管理类
 */
public class SongPlayManager {
    private static final String TAG = "SongPlayManager";
    private String CHECK_MUSIC_URL = "check/music";

    private static SongPlayManager instance;
    //播放到第几首歌曲
    private int currentSongIndex;
    //播放列表
    private List<SongInfo> songList = new ArrayList<>();
    //维护一个哈希表， key是 SongId, value 是 isMusicCanPlay，如果一首歌已经知道它是否可以播放，就把它放在这个哈希表里面
    private HashMap<String, Boolean> musicCanPlayMap;
    //设置监听器
    private SongPlayListener songListener;

    public SongPlayManager() {
        musicCanPlayMap = new HashMap<>();
        musicCanPlayMap.clear();
        songListener = new SongPlayListener();
        StarrySky.with().addPlayerEventListener(songListener);
    }
    public static SongPlayManager getInstance(){
        if(instance == null){
            synchronized (SongPlayManager.class){
                if(instance == null){
                    instance = new SongPlayManager();
                }
            }
        }
        return instance;
    }
    /**
     * 同时播放这首歌，需要在进入这首歌界面之前重置下状态
     * @param info 歌曲信息
     */
    public void clickASong(SongInfo info) {
        if(isPlaying()){
            LogUtil.d(TAG,"isPlaying");

        }else if(isPaused()){
            LogUtil.d(TAG,"isPaused");

        }else if(isIdle()){
            LogUtil.d(TAG,"isIdle");
            //空闲中 则直接播放该歌曲
            addSongAndPlay(info);

        }else{
            LogUtil.d(TAG, "no idle,no playing ,no paused state:" + MusicManager.getInstance().getState());
        }
    }

    /**
     * 添加一首歌并播放
     * @param info
     */
    public void addSongAndPlay(SongInfo info) {
        if(info == null){
            LogUtil.d(TAG,"songInfo is null");
            return;
        }
        currentSongIndex = addSong(info);
        checkMusic(info.getSongId());
    }
    /**
     * 添加一首歌曲到列表，并返回这首歌所在的位置
     */
    private int addSong(SongInfo info) {
        //查重
        if(songList.contains(info)){
            for (int i = 0; i < songList.size(); i++) {
                if(info.getSongId().equals(songList.get(i).getSongId())){
                    return i;
                }
            }
            return songList.size() - 1;
        }else{
            songList.add(info);
            return songList.size() - 1;
        }
    }
    private void checkMusic(String songId) {
        if(songId == null){
            LogUtil.d(TAG,"songId is null");
            return;
        }
        if(musicCanPlayMap.get(songId) == null){
            //如果一首歌还没有去检测它 是否可以播放，则就去做检测
            setOnSongCanPlayListener(new OnSongListener() {
                @Override
                public void onSongCanPlaySuccess(MusicCanPlayBean bean) {
                    if (bean.isSuccess()) {
                        musicCanPlayMap.put(songId, true);
                    } else {
                        musicCanPlayMap.put(songId, false);
                    }
                    playMusic(songId);
                }

                @Override
                public void onSongCnaPlayFail(String e) {
                    ToastUtils.show(e);
                }
            },songId);
        }else {
            //如果一首歌曲之前已经检测过了，则直接调用结果即可
            playMusic(songId);
        }
    }

    /**
     * 根据是否可以播放去 播放歌曲/弹出吐司
     */
    private void playMusic(String songId) {
        LogUtil.d(TAG, "songId :" + songId + "music size : " + songList.size());
        if(musicCanPlayMap.get(songId) || judgeContainsStr(songId)) {
            LogUtil.d(TAG, "music can play");
           StarrySky.with().playMusic(songList, currentSongIndex);
            SharePreferenceUtil.getInstance(App.getContext()).saveLatestSong(songList.get(currentSongIndex));
        }else{
            //弹出Toast
            LogUtil.d(TAG, "music can not play");
            ToastUtils.show("本歌曲不能播放，可能是没有版权Or你不是尊贵的VIP用户");
        }
    }

    public void setOnSongCanPlayListener(OnSongListener listener ,String id){
        Request.Builder builder = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(ApiService.BASE_URL + CHECK_MUSIC_URL).newBuilder();
        urlBuilder.addQueryParameter("id", id);
        Request request = builder.url(urlBuilder.build()).build();
        LogUtil.d(TAG, "request 请求头:" + request.toString());
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    listener.onSongCnaPlayFail(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dstStr = getErrorCodeString(response);
                LogUtil.d(TAG, "dstStr : " + dstStr);
                MusicCanPlayBean bean = GsonUtil.formJson(dstStr, MusicCanPlayBean.class);
                if (listener != null && bean != null) {
                    listener.onSongCanPlaySuccess(bean);
                } else {
                    listener.onSongCnaPlayFail("response is null");
                }
            }
        });

    }
    /**
     * 是否 当前有歌曲播放 暂停状态
     */
    public boolean isPaused() {
        return StarrySky.with().isPaused();
    }

    /**
     *是否正在播放歌曲
     */
    public boolean isPlaying() {
        return StarrySky.with().isPlaying();
    }
    /**
     * 当前播放器 是否是 空闲状态
     */
    public boolean isIdle(){
        return StarrySky.with().isIdea();
    }

    public void pauseMusic() {
        if(isPlaying()){
            StarrySky.with().pauseMusic();
        }
    }

    public void clickBottomControllerPlay(SongInfo songInfo) {
        if(isPaused()){
            playMusic();
        }else if(isIdle()){
            addSongAndPlay(songInfo);
        }

    }
    /**
     * 恢复播放
     */
    public void playMusic(){
        StarrySky.with().playMusic();

    }

    /**
     * 获取媒体时长，单位毫秒
     * @return
     */
    public long getDuration(){
        return StarrySky.with().getDuration();
    }

    /**
     * 获取播放位置 毫秒为单位
     * @return
     */
    public long  getPlayingPosition(){
        return StarrySky.with(). getPlayingPosition();
    }
    public interface OnSongListener{
        void onSongCanPlaySuccess(MusicCanPlayBean bean);

        void onSongCnaPlayFail(String e);
    }

    private String getErrorCodeString(Response response) {
        String res = "";
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        if (!bodyEncoded(response.headers())) {
            BufferedSource source = responseBody.source();
            try {
                source.request(Long.MAX_VALUE); // Buffer the entire body.
            } catch (IOException e) {
                e.printStackTrace();
            }
            Buffer buffer = source.buffer();

            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(Charset.forName("UTF-8"));
                } catch (UnsupportedCharsetException e) {
                    LogUtil.e(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }

            if (contentLength != 0) {
                res = buffer.clone().readString(charset);
            }
        }
        return res;
    }
    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
    /**
     * 该方法主要使用正则表达式来判断字符串中是否包含字母
     */
    public boolean judgeContainsStr(String cardNum) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }
    /**
     * 播放监听器
     */
    public class SongPlayListener implements OnPlayerEventListener {


        @Override
        public void onMusicSwitch(SongInfo songInfo) {

        }

        @Override
        public void onPlayerStart() {
            //开始播放
            LogUtil.d(TAG,"onPlayerStart");
            EventBus.getDefault().post(new MusicStartEvent(songList.get(currentSongIndex)));
        }

        @Override
        public void onPlayerPause() {
            LogUtil.d(TAG, "onPlayerPause");
            EventBus.getDefault().post(new MusicPauseEvent());

        }

        @Override
        public void onPlayerStop() {
            LogUtil.d(TAG, "onPlayerStop");
        }

        @Override
        public void onPlayCompletion(SongInfo songInfo) {
            LogUtil.d(TAG, "onPlayCompletion");
            playMusicNext();


        }

        @Override
        public void onBuffering() {

        }

        @Override
        public void onError(int errorCode, String errorMsg) {

        }
    }
    /**
     * 播放下一首歌曲，根据不同的模式来
     */
    private void playMusicNext() {


    }


}
