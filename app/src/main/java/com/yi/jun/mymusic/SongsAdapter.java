package com.yi.jun.mymusic;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yi.jun.mymusic.network.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    private class DownloadSong extends AsyncTask<String, Void, File> {

        @Override
        protected File doInBackground(String... params) {
            String songId = params[0];
            URL songURL = NetworkUtils.fetchSongUrl(songId);
            Log.i("TEST", "get the song url, start to download");
            File file = getTempFile(context, songURL.toString());
            return file;
        }

        @Override
        protected void onPostExecute(File file) {
            Log.i("TEST", "onPostExecute");

            try {
                Log.i("TEST", "startPlay");

                MediaPlayer mPlayer = MediaPlayer.create(context, Uri.fromFile(file));
                mPlayer.start();

            } catch (Exception e) {
                // TODO: handle exception
            }

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    @Override
    public int getItemCount() {
        return songstList.size();
    }


    public File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            Log.i("TEST", "saving file " + fileName);
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }

}
