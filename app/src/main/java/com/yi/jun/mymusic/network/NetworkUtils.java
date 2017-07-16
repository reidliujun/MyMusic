package com.yi.jun.mymusic.network;

import android.util.Log;

import com.yi.jun.mymusic.Artist;
import com.yi.jun.mymusic.Songs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by junliu on 7/16/17.
 */

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getName();
    private static String BASE_URL = "http://192.168.10.43:3000/";

    public NetworkUtils() {
    }


    public static List<Artist> fetchTopArtistsData(){
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequstTopArtist();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News} object
        List<Artist> artistList = extractArtistsFromJSON(jsonResponse);
        // Return the {@link Event}
        return artistList;

    }

    public static Artist fetchArtistData(String id) {
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequestArtist(id);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News} object
        Artist artist = extractArtistFromJSON(jsonResponse);
        // Return the {@link Event}
        return artist;
    }

    private static List<Artist> extractArtistsFromJSON(String jsonResponse) {
        List<Artist> artistList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray results = jsonObject.getJSONArray("artists");
            for (int i = 0; i < results.length(); i++) {
                JSONObject currentArtist = results.getJSONObject(i);
                String name = currentArtist.getString("name");
                String id = currentArtist.getString("id");
                int albumSize = currentArtist.getInt("albumSize");
                String imgUrl = currentArtist.getString("img1v1Url");
                artistList.add(new Artist(name, id, albumSize, imgUrl));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return artistList;
    }

    private static Artist extractArtistFromJSON(String jsonResponse) {
        Artist artist = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject artistDetail = jsonObject.getJSONObject("artist");
            String artistName = artistDetail.getString("name");
            String artistID = artistDetail.getString("id");
            String briefDesc = artistDetail.getString("briefDesc");
            String alias = artistDetail.getString("alias");
            String trans = artistDetail.getString("trans");
            artist = new Artist(artistName, artistID);
            artist.setBriefDesc(briefDesc);
            artist.setAlias(alias);
            artist.setTrans(trans);
            List<Songs> hotSongsList= new ArrayList<>();

            JSONArray hotSongs = jsonObject.getJSONArray("hotSongs");
            for (int i = 0; i < hotSongs.length(); i++) {
                JSONObject currentSong = hotSongs.getJSONObject(i);
                String songName = currentSong.getString("name");
                String songId = currentSong.getString("id");
                JSONObject albumInfo = currentSong.getJSONObject("al");
                String albumName = albumInfo.getString("name");
                String albumUrl = albumInfo.getString("picUrl");

                Songs mySong = new Songs(songName, songId);
                mySong.setAlbumName(albumName);
                mySong.setAlbumUrl(albumUrl);
                hotSongsList.add(mySong);
            }
            artist.setSongsList(hotSongsList);
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return artist;
    }

    private static String makeHttpRequestArtist(String id) throws IOException{
        URL urlString = new URL(BASE_URL + "artists?id=" + id);
        return makeHttpRequest(urlString);
    }

    private static String makeHttpRequstTopArtist() throws IOException{
        URL urlString = new URL(BASE_URL + "top/artists");
        return makeHttpRequest(urlString);
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            if (url == null) {
                return jsonResponse;
            }

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.i(LOG_TAG, jsonResponse);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the Artist JSON results", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return jsonResponse;
        }
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    
    
}
