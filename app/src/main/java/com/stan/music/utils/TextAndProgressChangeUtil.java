package com.stan.music.utils;

import com.stan.music.bean.LrcEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Stan
 * Date: 2019/11/25 11:28
 * Description: 进度条和歌词更新工具
 */
public class TextAndProgressChangeUtil {
    private List<LrcEntry> mLrcEntryList = new ArrayList<>();

    /**
     * 解析歌词
     * @param mainLrcText
     * @param secondLrcText
     */
    public void loadLrc(String mainLrcText, String secondLrcText) {
        mLrcEntryList.clear();
        String[] lrc =new String[2];
        lrc[0] = mainLrcText;
        lrc[1] = secondLrcText;
        List<LrcEntry> parseList = LrcUtils.parseList(lrc);
        if(parseList != null && !parseList.isEmpty()){
            mLrcEntryList.addAll(parseList);
        }
        Collections.sort(mLrcEntryList);
    }

    public String updateTime(long time) {
        if(!lrcNotEmpty()){
            return null;
        }
        int line = findShowLine(time);

        return mLrcEntryList.get(line).getText();
    }
    /**
     * 歌词 是否 不为空
     */
    private boolean lrcNotEmpty() {
        return !mLrcEntryList.isEmpty();
    }
    /**
     * 用二分查找 对应的时间应该显示第几行歌词
     */
    private int findShowLine(long time) {
        int left = 0;
        int right = mLrcEntryList.size();
        while (left <= right) {
            int middle = (left + right) / 2;
            long middleTime = mLrcEntryList.get(middle).getTime();

            if (time < middleTime) {
                right = middle - 1;
            } else {
                if (middle + 1 >= mLrcEntryList.size() || time < mLrcEntryList.get(middle + 1).getTime()) {
                    return middle;
                }

                left = middle + 1;
            }
        }

        return 0;
    }
}
