package com.stan.music.utils;

import android.text.TextUtils;
import android.text.format.DateUtils;

import com.stan.music.bean.LrcEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Stan
 * Date: 2019/11/25 11:47
 * Description: 用来解析歌词的工具类
 */
class LrcUtils {
    public static List<LrcEntry> parseList(String[] lrc) {
        if (lrc == null || lrc.length != 2 || TextUtils.isEmpty(lrc[0])) {
            return null;
        }
        List<LrcEntry> mainListEntry = parseList(lrc[0]);
        List<LrcEntry> secondListEntry = parseList(lrc[1]);
        /**
         * 设置双语歌词
         */
        if (mainListEntry != null && secondListEntry != null) {
            for (LrcEntry mainEntry : mainListEntry) {
                for (LrcEntry secondEntry : secondListEntry){
                    if(mainEntry.getTime() == secondEntry.getTime()){
                        mainEntry.setSecondText(secondEntry.getText());
                    }
                }
            }
        }

        return mainListEntry;
    }

    private static List<LrcEntry> parseList(String lrcText) {
        if (TextUtils.isEmpty(lrcText)) {
            return null;
        }
        List<LrcEntry> entry = new ArrayList<>();
        String[] array = lrcText.split("\\n");
        for (String line : array) {
            List<LrcEntry> list = parseLine(line);
            if (list != null && !list.isEmpty()) {
                entry.addAll(list);
            }
        }
        //排序
        Collections.sort(entry);
        return entry;
    }

    /**
     * 用正则匹配解析一行歌词
     * [00:17.65]让我掉下眼泪的
     */
    private static List<LrcEntry> parseLine(String line) {
        if (TextUtils.isEmpty(line)) {
            return null;
        }
        line = line.trim();
        Matcher lineMatcher = Pattern.compile("((\\[\\d\\d:\\d\\d\\.\\d{2,3}\\])+)(.+)").matcher(line);
        if (!lineMatcher.matches()) {
            return null;
        }
        String times = lineMatcher.group(1);
        String text = lineMatcher.group(3);
        List<LrcEntry> entryList = new ArrayList<>();

        // [00:17.65]
        Matcher timeMatcher = Pattern.compile("\\[(\\d\\d):(\\d\\d)\\.(\\d{2,3})\\]").matcher(times);
        while (timeMatcher.find()) {
            long min = Long.parseLong(timeMatcher.group(1));
            long sec = Long.parseLong(timeMatcher.group(2));
            String milString = timeMatcher.group(3);
            long mil = Long.parseLong(milString);
            // 如果毫秒是两位数，需要乘以10
            if (milString.length() == 2) {
                mil = mil * 10;
            }
            long time = min * DateUtils.MINUTE_IN_MILLIS + sec * DateUtils.SECOND_IN_MILLIS + mil;
            entryList.add(new LrcEntry(time, text));
        }
        return entryList;
    }
}
