package com.stan.music.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.stan.music.R;
import com.stan.music.base.BaseAdapter;
import com.stan.music.bean.PlayListItemBean;
import com.stan.music.bean.UserPlaylistBean;
import com.stan.music.widget.RoundRectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Stan
 * Date: 2019/11/12 14:38
 * Description: ${DESCRIPTION}
 */
public class UserPlaylistAdapter extends BaseAdapter<RecyclerView.ViewHolder, PlayListItemBean> {
    private boolean isShowSmartPlay = false;
    private Context context;
    private OnPlayListItemClickListener listener;
    private List<PlayListItemBean> lists = new ArrayList<>();

    public UserPlaylistAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void notifyDataSetChanged(List<PlayListItemBean> dataList) {
        lists = dataList;
        notifyDataSetChanged();
    }

    public void setShowSmartPlay(boolean showSmartPlay) {
        isShowSmartPlay = showSmartPlay;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(getInflater().inflate(R.layout.item_playlist_fragment,viewGroup,false)) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof UserPlaylistAdapter.ViewHolder) {
            UserPlaylistAdapter.ViewHolder vh = (UserPlaylistAdapter.ViewHolder) viewHolder;
            PlayListItemBean bean = lists.get(i);
            vh.setPlayListItemInfo(context, bean, i);
            vh.onSetListClickListener(listener, i);
        }
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private RoundRectView ivCover;
        private TextView tvName, tvPlayListInfo;
        private LinearLayout llItem;
        private RelativeLayout rlSmartPlay;
        private ImageView ivMoreInfo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvName = itemView.findViewById(R.id.tv_playlist_name);
            tvPlayListInfo = itemView.findViewById(R.id.tv_playlist_info);
            llItem = itemView.findViewById(R.id.ll_item);
            rlSmartPlay = itemView.findViewById(R.id.rl_heart_play);
            ivMoreInfo = itemView.findViewById(R.id.iv_more_info);
        }

        @SuppressLint("SetTextI18n")
        void setPlayListItemInfo(Context mContext, PlayListItemBean bean, int position) {
            Glide.with(mContext).load(bean.getCoverUrl()).transition(new DrawableTransitionOptions().crossFade()).into(ivCover);
            tvName.setText(bean.getPlayListName());
            long playcount = bean.getPlayCount();
            String count;
            if (playcount >= 10000) {
                playcount = playcount / 10000;
                count = playcount + "万次";
            } else {
                count = playcount + "次";
            }
            if (bean.isDefaultAvatar()) {
                tvPlayListInfo.setText(bean.getSongNumber() + "首");
            } else {
                tvPlayListInfo.setText(bean.getSongNumber() + "首，by " + bean.getPlaylistCreator());
            }
            if (isShowSmartPlay && position == 0) {
                rlSmartPlay.setVisibility(View.VISIBLE);
                ivMoreInfo.setVisibility(View.GONE);
            } else {
                rlSmartPlay.setVisibility(View.GONE);
                ivMoreInfo.setVisibility(View.VISIBLE);
            }
        }

        void onSetListClickListener(OnPlayListItemClickListener listener, int i) {
            rlSmartPlay.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSmartPlayClick(getAdapterPosition());
                }
            });

            llItem.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPlayListItemClick(i);
                }
            });
            ivMoreInfo.setOnClickListener( v -> {
                if(listener != null){
                    listener.onShowMoreDialog(i,isShowSmartPlay);
                }
            });
        }
    }

    public interface OnPlayListItemClickListener {
        void onPlayListItemClick(int position);

        void onSmartPlayClick(int position);

        void onShowMoreDialog(int position, boolean isShowSmartPlay);
    }

    public void setListener(OnPlayListItemClickListener listener) {
        this.listener = listener;
    }
}
