package com.yi.jun.mymusic;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Artist>> {

//    private SongsAdapter mAdapter;
    private ArtistsAdapter artistsAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        artistsAdapter = new ArtistsAdapter(this, new ArrayList<Artist>());
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(artistsAdapter);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            // Initialize the loader if there is internet connection
            getLoaderManager().initLoader(1, null, this);
        }

    }


    @Override
    public Loader<List<Artist>> onCreateLoader(int i, Bundle bundle) {
        return new ArtistsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Artist>> loader, List<Artist> artistsList) {
        artistsAdapter = new ArtistsAdapter(this, new ArrayList<Artist>());
        if (artistsList != null && !artistsList.isEmpty()) {
            artistsAdapter = new ArtistsAdapter(this, artistsList);
            mRecyclerView.setAdapter(artistsAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Artist>> loader) {
        artistsAdapter = new ArtistsAdapter(this, new ArrayList<Artist>());
    }
}
