package com.example.albumplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class NowListening extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    int position;
    SeekBar seekBar;
    boolean onRepeatMode = false;
//    ArrayList<Song> songs;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    MediaPlayer.OnCompletionListener onCompletion = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (position < 8) {
                position += 1;
                play(position);
            } else {
                releaseMediaPlayer();
            }

        }
    };
    ImageView imageView;
    ImageView repeatModeView;
    TextView songName, artistName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_listening);
        setRepeatMode();

        position = getIntent().getIntExtra("position", 0);

        imageView = findViewById(R.id.listening_image);
        songName = findViewById(R.id.listening_song_name);
        artistName = findViewById(R.id.listening_artist_name);



        final ImageView play_pause = findViewById(R.id.listening_play_pause);
        ImageView previous = findViewById(R.id.listening_previous);
        ImageView next = findViewById(R.id.listening_next);

        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAudioManager.isMusicActive()) {
                    mMediaPlayer.pause();
                    play_pause.setImageResource(R.drawable.ic_play);
                }
                if (!mAudioManager.isMusicActive()) {
                    mMediaPlayer.start();
                    play_pause.setImageResource(R.drawable.ic_pause);
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position -= 1;
                if (position >= 0 && position <= 8) {
                    releaseMediaPlayer();
                    play(position);
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position += 1;
                if (position >= 0 && position <= 8) {
                    releaseMediaPlayer();
                    play(position);
                }
                if (position == 9){
                    position = 0;
                    play(position);
                }
            }
        });
        repeatModeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRepeatMode){
                    onRepeatMode = false;
                }
                else {
                    onRepeatMode = true;
                }
                setRepeatMode();
            }
        });
        play(position);



    }

    private void setRepeatMode() {
        repeatModeView = findViewById(R.id.repeat_mode_view);

        if (onRepeatMode){
            repeatModeView.setImageResource(R.drawable.ic_baseline_repeat_24_pink);

        }
        else {
            repeatModeView.setImageResource(R.drawable.ic_baseline_repeat_24);
        }

    }

    public void play(int position) {
        Song song = MainActivity.songs.get(position);

        imageView.setImageResource(song.getAlbumImageId());


        songName.setText(song.getSongName());


        artistName.setText(song.getArtistName());


        releaseMediaPlayer();

        audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                    mMediaPlayer.pause();
                }
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    releaseMediaPlayer();
                }
                if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    mMediaPlayer.start();
                }
            }
        };
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int audioFocus = mAudioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (audioFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mMediaPlayer = MediaPlayer.create(this, song.getSongId());
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(onCompletion);
        }


        setSeekBar();


    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(audioFocusChangeListener);
        }

    }

    public void setSeekBar(){
        final TextView progressView = findViewById(R.id.progress_text_view);
        TextView totalDurationView = findViewById(R.id.total_duration_text_view);
        seekBar = findViewById(R.id.song_seek_bar);

        final int maxDuration = mMediaPlayer.getDuration();
        seekBar.setMax(maxDuration);

        String maxDurationFormatted = formatTime(maxDuration);
        totalDurationView.setText(maxDurationFormatted);

        final Handler mHandler = new Handler();
        NowListening.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(mMediaPlayer != null){
                    long mCurrentPosition = mMediaPlayer.getCurrentPosition();
                    String time = formatTime(mCurrentPosition);
                    progressView.setText(time);
                    seekBar.setProgress((int) mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mMediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }

    public String formatTime(long time){
        @SuppressLint("DefaultLocale")
        String formattedTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(time),
                TimeUnit.MILLISECONDS.toSeconds(time)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );
        return formattedTime;
    }


}
