package com.example.albumplayer;

import java.io.Serializable;

public class Song implements Serializable {
    private String mSongName, mArtistName;
    private int mSongId, mAlbumImageId;

    public Song(String songName, String artistName, int albumImageId, int songId) {
        mSongId = songId;
        mSongName = songName;
        mArtistName = artistName;
        mAlbumImageId = albumImageId;
    }

    public String getSongName() {
        return mSongName;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public int getSongId() {
        return mSongId;
    }

    public int getAlbumImageId() {
        return mAlbumImageId;
    }
}
