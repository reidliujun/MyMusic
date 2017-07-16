package com.yi.jun.mymusic;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.yi.jun.mymusic.network.NetworkUtils;

import java.util.List;

/**
 * Created by junliu on 7/16/17.
 */

public class ArtistsLoader extends AsyncTaskLoader<List<Artist>> {


    public ArtistsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Artist> loadInBackground() {
        List<Artist> artistsList= NetworkUtils.fetchTopArtistsData();
        return artistsList;
    }
}
