package com.stan.music;

/**
 * Author: Stan
 * Date: 2019/11/4 10:51
 * Description: ${DESCRIPTION}
 */
public interface Constants {
    String SHARED_PREFERENCE_FILE_NAME = "stanMusic_sp";
    interface SpKey{
        String AUTH_TOKEN = "authToken";
        String PHONE_NUMBER = "phoneNumber";
        String USER_INFO = "userInfo";
        String LATEST_SONG = "latestSong";
    }
}
