package com.example.albumplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final  static ArrayList<Song> songs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songs.add(new Song("Blank Space", "Taylor Swift", R.drawable.logo_1989, R.raw.blank_space));
        songs.add(new Song("red", "Taylor Swift", R.drawable.red, R.raw.red));
        songs.add(new Song("Shake it off", "Taylor Swift", R.drawable.logo_1989, R.raw.shake_it_off));
        songs.add(new Song("sunflower", "Post Malone", R.drawable.sunflower, R.raw.sunflower));
        songs.add(new Song("Uptown Funk", "Mark Ronson & Bruno Mars", R.drawable.uptown_funk, R.raw.uptown_funk));
        songs.add(new Song("Versace on the floor", "Bruno Mars", R.drawable.versace_on_the_floor, R.raw.versace_on_the_floor));
        songs.add(new Song("You belong with me", "Taylor Swift", R.drawable.you_belong_with_me, R.raw.you_belong_with_me));
        songs.add(new Song("Call me Maybe", "carly Rae Jepson", R.drawable.call_me_maybe, R.raw.call_me_maybe));
        songs.add(new Song("Lost Stars", "Adam Levine", R.drawable.lost_stars, R.raw.lost_stars));

        final CustomArrayAdapter adapter = new CustomArrayAdapter(this, songs);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song currentSong = songs.get(position);
                Intent intent = new Intent(getApplicationContext(), NowListening.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

    }
}
