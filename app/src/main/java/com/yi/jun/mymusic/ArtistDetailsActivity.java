package com.yi.jun.mymusic;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by junliu on 7/16/17.
 */

public class ArtistDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Artist> {

    private TextView artistName;
    private TextView artistDetails;
    private ImageView imageView;
    private SongsAdapter songsAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artist_details);
        artistName = (TextView) findViewById(R.id.artist_name);
        artistDetails = (TextView) findViewById(R.id.artist_desc);
        imageView = (ImageView) findViewById(R.id.artist_new_pic);
        songsAdapter = new SongsAdapter(this, new ArrayList<Songs>());
        mRecyclerView = (RecyclerView) findViewById(R.id.songs_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(songsAdapter);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            // Initialize the loader if there is internet connection
            getLoaderManager().initLoader(1, null, this);
        }
    }

    @Override
    public Loader<Artist> onCreateLoader(int i, Bundle bundle) {
        Log.i("TEST", getIntent().getStringExtra("songId"));
        return new ArtistDetailsLoader(this, getIntent().getStringExtra("songId"));
    }

    @Override
    public void onLoadFinished(Loader<Artist> loader, Artist artist) {

        artistName.setText(artist.getName());
        artistDetails.setText(artist.getBriefDesc());

        Picasso.with(this).load(artist.getImageUrl()).resize(25,25).centerCrop().into(imageView);

        songsAdapter = new SongsAdapter(this, new ArrayList<Songs>());

        if (artist.getSongsList() != null && !artist.getSongsList().isEmpty()) {
            songsAdapter = new SongsAdapter(this, artist.getSongsList());
            mRecyclerView.setAdapter(songsAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Artist> loader) {
        songsAdapter = new SongsAdapter(this, new ArrayList<Songs>());
    }
}
