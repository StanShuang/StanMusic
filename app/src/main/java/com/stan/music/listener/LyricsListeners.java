package com.stan.music.listener;

import com.stan.music.bean.LyricBean;

/**
 * Author: Stan
 * Date: 2019/11/22 15:39
 * Description: 获取歌词的监听器
 */
public interface LyricsListeners {
    void getLyricsSuccess(LyricBean bean);
    void getLyricsFail(String e);
}
