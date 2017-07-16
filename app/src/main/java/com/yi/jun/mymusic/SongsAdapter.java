package com.yi.jun.mymusic;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by junliu on 7/16/17.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    private List<Songs> songstList;
    private Context context;

    public SongsAdapter(Context context, List<Songs> songstList) {
        this.songstList = songstList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView songName;
        public ImageView imageView;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            songName = (TextView) itemView.findViewById(R.id.song_name);
            imageView = (ImageView) itemView.findViewById(R.id.album_image);
            cardView = (CardView) itemView.findViewById(R.id.song_card_view);
        }
    }

    @Override
    public SongsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View songView = inflater.inflate(R.layout.song_item, parent, false);
        SongsAdapter.ViewHolder viewHolder = new SongsAdapter.ViewHolder(songView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SongsAdapter.ViewHolder viewHolder, int position) {
        final Songs song = songstList.get(position);

        final ImageView imageView = viewHolder.imageView;
        final TextView songName = viewHolder.songName;
        final CardView cardView = viewHolder.cardView;
        songName.setText(song.getName());
        Picasso.with(context).load(song.getAlbumUrl()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return songstList.size();
    }


}
