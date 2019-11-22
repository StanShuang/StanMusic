package com.stan.music.manager.event;

import com.lzx.starrysky.provider.SongInfo;

/**
 * Author: Stan
 * Date: 2019/11/22 13:54
 * Description:播放歌曲的Event
 */
public class MusicStartEvent {
    SongInfo songInfo;
    public MusicStartEvent(SongInfo songInfo) {
        this.songInfo = songInfo;
    }

    public SongInfo getSongInfo() {
        return songInfo;
    }

    public void setSongInfo(SongInfo songInfo) {
        this.songInfo = songInfo;
    }
}
