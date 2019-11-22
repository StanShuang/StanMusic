package com.stan.music.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stan.music.R;

/**
 * Author: Stan
 * Date: 2019/11/14 18:00
 * Description: 收藏的更多弹窗
 */
public class MoreDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    public static Fragment getInstance(boolean isMine,String name) {
        MoreDialogFragment fragment = new MoreDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putBoolean("isMine",isMine);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_more_fragmen, container);
        TextView itemTextView = view.findViewById(R.id.tv_name);
        String itemName = getArguments().getString("name");
        boolean isMine = getArguments().getBoolean("isMine");
        MineItemView itemDownload = view.findViewById(R.id.item_download);
        MineItemView itemShare = view.findViewById(R.id.item_share);
        MineItemView itemEdit = view.findViewById(R.id.item_edit);
        MineItemView itemDelete = view.findViewById(R.id.item_delete);
        itemDownload.setOnClickListener(this);
        itemShare.setOnClickListener(this);
        itemEdit.setOnClickListener(this);
        itemDelete.setOnClickListener(this);
        itemTextView.setText("歌单:"+itemName);
        if(!isMine){
            itemEdit.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_download:

                break;
            case R.id.item_share:

                break;
            case R.id.item_edit:

                break;
            case R.id.item_delete:

                break;
            default:
                break;
        }

    }
}
