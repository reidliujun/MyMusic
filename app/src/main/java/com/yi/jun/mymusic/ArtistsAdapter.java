package com.yi.jun.mymusic;

import android.content.Context;
import android.content.Intent;
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

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    private List<Artist> artistList;
    private Context context;


    public ArtistsAdapter(Context context, List<Artist> artistList) {
        this.artistList = artistList;
        this.context = context;
    }


    @Override
    public ArtistsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View artistView = inflater.inflate(R.layout.artist_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(artistView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArtistsAdapter.ViewHolder viewHolder, int position) {
        final Artist artist = artistList.get(position);

        final ImageView imageView = viewHolder.imageView;
        final TextView artistName = viewHolder.artistName;
        final CardView cardView = viewHolder.cardView;
        artistName.setText(artist.getName());
        Picasso.with(context).load(artist.getImageUrl()).into(imageView);

        cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArtistDetailsActivity.class);
                intent.putExtra("artistId", artist.getId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView artistName;
        public ImageView imageView;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.artist_card_view);
            artistName = (TextView) itemView.findViewById(R.id.artist_name);
            imageView = (ImageView) itemView.findViewById(R.id.artist_pic);
        }
    }
}
