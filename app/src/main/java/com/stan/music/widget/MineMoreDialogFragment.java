package com.stan.music.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stan.music.R;

/**
 * Author: Stan
 * Date: 2019/11/18 10:50
 * Description: ${DESCRIPTION}
 */
public class MineMoreDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    public static Fragment getInstance(boolean isMine) {
        MineMoreDialogFragment fragment = new MineMoreDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isMine",isMine);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_main_more_fragmen, container);
        TextView itemTextView = view.findViewById(R.id.tv_name);
        boolean isMine = getArguments().getBoolean("isMine");
        MineItemView itemDownload = view.findViewById(R.id.item_download);
        MineItemView itemShare = view.findViewById(R.id.item_share);
        MineItemView itemEdit = view.findViewById(R.id.item_edit);
        MineItemView itemDelete = view.findViewById(R.id.item_delete);
        itemDownload.setOnClickListener(this);
        itemShare.setOnClickListener(this);
        itemEdit.setOnClickListener(this);
        itemDelete.setOnClickListener(this);
        itemShare.setVisibility(View.VISIBLE);
        if(isMine){
            itemTextView.setText(R.string.create_song);
            itemDownload.setVisibility(View.VISIBLE);
            itemEdit.setVisibility(View.VISIBLE);
            itemDelete.setVisibility(View.VISIBLE);
        }else{
            itemTextView.setText(R.string.collection_song);
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
