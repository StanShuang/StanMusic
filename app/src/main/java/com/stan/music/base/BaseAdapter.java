package com.stan.music.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author: Stan
 * Date: 2019/11/12 14:40
 * Description: ${DESCRIPTION}
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder,T> extends RecyclerView.Adapter<VH> {
    private LayoutInflater inflater;
    private Context context;
    public BaseAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public LayoutInflater getInflater() {
        return inflater;
    }
    public abstract void notifyDataSetChanged(List<T> dataList);
}
