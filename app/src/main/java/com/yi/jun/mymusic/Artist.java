package com.yi.jun.mymusic;

import java.util.List;

/**
 * Created by junliu on 7/16/17.
 */

public class Artist {
    private String name;
    private String id;
    private int albumSize;
    private String imageUrl;
    private String briefDesc;
    private String trans;
    private String alias;
    private List<Songs> hotSongsList;


    public Artist(String name, String id, int albumSize, String imageUrl) {
        this.name = name;
        this.id = id;
        this.albumSize = albumSize;
        this.imageUrl = imageUrl;
    }

    public Artist(String name, String id) {
        this.name = name;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAlbumSize() {
        return albumSize;
    }

    public void setAlbumSize(int albumSize) {
        this.albumSize = albumSize;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBriefDesc() {
        return briefDesc;
    }

    public void setBriefDesc(String briefDesc) {
        this.briefDesc = briefDesc;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<Songs> getSongsList() {
        return hotSongsList;
    }

    public void setSongsList(List<Songs> hotSongsList) {
        this.hotSongsList = hotSongsList;
    }
}
