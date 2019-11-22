package com.stan.music.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzx.starrysky.provider.SongInfo;
import com.stan.music.R;
import com.stan.music.base.BaseAdapter;
import com.stan.music.manager.SongPlayManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Stan
 * Date: 2019/11/19 16:45
 * Description: ${DESCRIPTION}
 */
public class SongListAdapter extends BaseAdapter<RecyclerView.ViewHolder, SongInfo> {
    private static final String TAG = "SongListAdapter";
    private Context mContext;
    public List<SongInfo> lists = new ArrayList<>();

    public SongListAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public void notifyDataSetChanged(List<SongInfo> dataList) {
        lists = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(getInflater().inflate(R.layout.item_songlist,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof ViewHolder){
            if(lists == null || lists.isEmpty()){
                return;
            }
            ViewHolder vh = (ViewHolder) viewHolder;
            SongInfo bean = lists.get(i);
            vh.setSongInfo(mContext,bean,i);
            vh.setSongClick(bean,i);
        }

    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout rlSong;
        private TextView tvSongNum;
        private TextView tvSongTitle;
        private TextView tvSongSubtitle;
        private TextView tvSongAuthor;
        private ImageView ivSongVolume;
        private ImageView ivSongExclusive;
        private ImageView ivSongSQ;
        private ImageView ivSongVideo;
        private ImageView ivSongMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlSong = itemView.findViewById(R.id.rl_song);
            tvSongNum = itemView.findViewById(R.id.tv_song_num);
            tvSongTitle = itemView.findViewById(R.id.tv_song_title);
            tvSongSubtitle = itemView.findViewById(R.id.tv_song_subtitle);
            tvSongAuthor = itemView.findViewById(R.id.tv_song_author);
            ivSongVolume = itemView.findViewById(R.id.iv_song_volume);
            ivSongExclusive = itemView.findViewById(R.id.iv_song_exclusive);
            ivSongSQ = itemView.findViewById(R.id.iv_song_sq);
            ivSongVideo = itemView.findViewById(R.id.iv_song_video);
            ivSongMore = itemView.findViewById(R.id.iv_song_more);
        }

        @SuppressLint("SetTextI18n")
        public void setSongInfo(Context mContext, SongInfo info, int i) {
            tvSongNum.setText(i+1+"");
            if(!TextUtils.isEmpty(info.getSongName())){
                tvSongTitle.setText(info.getSongName());
            }
            if(!TextUtils.isEmpty(info.getArtist())){
                if(!TextUtils.isEmpty(info.getAlbumName())){
                    tvSongAuthor.setText(info.getArtist() + " - "+info.getAlbumName());
                }else{
                    tvSongAuthor.setText(info.getArtist());
                }
            }

            if(!TextUtils.isEmpty(info.getTas())){
                tvSongSubtitle.setText("("+info.getTas() + ")");
            }else{
                if(!TextUtils.isEmpty(info.getSubtitle())){
                    tvSongSubtitle.setText("("+info.getSubtitle() + ")");
                }
            }

            ivSongExclusive.setVisibility(View.VISIBLE);
            ivSongSQ.setVisibility(View.VISIBLE);
            ivSongVideo.setVisibility(View.VISIBLE);
        }

        public void setSongClick(SongInfo info, int positon) {
            rlSong.setOnClickListener(v -> {
                //当点击正在播放的item时,跳转页面，否则播放改item
                SongPlayManager.getInstance().clickASong(info);
            });
        }
    }

}
