package com.stan.music.bean;

import android.support.annotation.NonNull;

/**
 * Author: Stan
 * Date: 2019/11/25 11:30
 * Description:一行歌词的实体类,因为要按照时间排序，所以需要实现Comparable
 * *用它来画到View上
 */
public class LrcEntry implements Comparable<LrcEntry> {
    //歌词所对应的时间
    private long time;
    //第一种语言的歌词的内容
    private String text;
    //第二种语言的歌词内容，一开始是空的，需要设置
    private String secondText;

    public LrcEntry(long time, String text) {
        this.time = time;
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSecondText() {
        return secondText;
    }

    public void setSecondText(String secondText) {
        this.secondText = secondText;
    }

    /**
     * 根据歌词时间来比较大小
     */
    @Override
    public int compareTo(@NonNull LrcEntry o) {
        if (o == null) {
            return -1;
        }
        return (int)(time - o.getTime());
    }
}
