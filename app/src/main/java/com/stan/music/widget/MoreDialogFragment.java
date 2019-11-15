package com.stan.music.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stan.music.R;

/**
 * Author: Stan
 * Date: 2019/11/14 18:00
 * Description: 收藏的更多弹窗
 */
public class MoreDialogFragment extends BaseDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view  = inflater.inflate(R.layout.dialog_more_fragmen,container);
        return view;
    }

}
