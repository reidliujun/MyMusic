package com.yi.jun.mymusic;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.yi.jun.mymusic.network.NetworkUtils;

/**
 * Created by junliu on 7/16/17.
 */

public class ArtistDetailsLoader extends AsyncTaskLoader<Artist> {

    private String artistId;

    public ArtistDetailsLoader(Context context, String artistId) {
        super(context);
        this.artistId = artistId;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Artist loadInBackground() {
        return NetworkUtils.fetchArtistData(artistId);
    }
}
