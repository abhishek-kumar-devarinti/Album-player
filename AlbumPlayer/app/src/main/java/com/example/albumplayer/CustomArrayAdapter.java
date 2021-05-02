package com.example.albumplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Song> {

    public CustomArrayAdapter(Context context, ArrayList<Song> songs) {
        super(context, 0, songs);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Song currentSong = getItem(position);
        TextView songName = listItemView.findViewById(R.id.song_name);
        songName.setText(currentSong.getSongName());

        TextView artistName = listItemView.findViewById(R.id.artist_name);
        artistName.setText(currentSong.getArtistName());

        ImageView image = listItemView.findViewById(R.id.image);
        image.setImageResource(currentSong.getAlbumImageId());

        return listItemView;
    }

}
