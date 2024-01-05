package com.example.musicplayer;

import static java.lang.System.exit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.Song;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SongsActivity extends AppCompatActivity {

    private ImageView imagepre,imagecontinue,imagestop,imagenext,imageViewDisc,imageSong,imageExit;
    private TextView textViewTitle,textViewTime,textViewTimeLast;
    private SeekBar seekBar;

    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        // liên kết với list
        Intent intent = getIntent();
        // Kiểm tra xem có dữ liệu trong Intent hay không
        if (intent != null && intent.hasExtra("selectedPosition")) {
            // Lấy vị trí bài hát từ Intent
            int selectedPosition = intent.getIntExtra("selectedPosition", 0);
            // Đặt vị trí bài hát cho SongsActivity
            position = selectedPosition;

        }

        imageExit = findViewById(R.id.imageExit);
        imageSong = findViewById(R.id.imageSong);
        imagecontinue = findViewById(R.id.imagecontinue);
        imagepre = findViewById(R.id.imagepre);
        imagestop = findViewById(R.id.imagestop);
        imagenext = findViewById(R.id.imagenext);
        textViewTime = findViewById(R.id.textViewTime);
        textViewTimeLast = findViewById(R.id.textViewTimeLast);
        textViewTitle = findViewById(R.id.textViewTitle);
        seekBar = findViewById(R.id.seekBar);
        imageViewDisc = findViewById(R.id.imageViewDisc);

        animation = AnimationUtils.loadAnimation(this,R.anim.disc_rotate);
        AddSong();
        KhoiTaoMediaPlayer();

        imageExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imagepre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if (position >= arraySong.size()) {
                    position = 0;
                }
                if(position > 0){
                    position = arraySong.size() - 1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                imagecontinue.setImageResource(R.drawable.pause);
                SetTime();
                UpdateTime();
            }
        });
        imagenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if (position >= arraySong.size()) {
                    position = 0;
                }
                if(position > arraySong.size() - 1){
                    position = 0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                SetTime();
                UpdateTime();
            }
        });
        imagestop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                imagecontinue.setImageResource(R.drawable.play);
                KhoiTaoMediaPlayer();
            }
        });
        imagecontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    //neu dang phat thi dung va doi thanh play
                    mediaPlayer.pause();
                    imagecontinue.setImageResource(R.drawable.play);
                }else{
                    //nguoc lai
                    mediaPlayer.start();
                    imagecontinue.setImageResource(R.drawable.pause);
                }
                SetTime();
                UpdateTime();
                imageViewDisc.startAnimation(animation);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }
    private void UpdateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    SimpleDateFormat dinhdanggio = new SimpleDateFormat("mm:ss");
                    textViewTime.setText(dinhdanggio.format(mediaPlayer.getCurrentPosition()));
                    // update progress seekBar
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    // kiem tra thoi gian bai hat khi ket thuc se chuyen bai
                    if (!mediaPlayer.isPlaying() && mediaPlayer.getCurrentPosition() > 0
                            && mediaPlayer.getCurrentPosition() >= mediaPlayer.getDuration()) {
                        position++;
                        if (position >= arraySong.size()) {
                            position = 0;
                        }
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        SetTime();
                        UpdateTime();
                    }
                } catch (Exception e) {
                    // Handle the exception, for example, by logging it or displaying an error message.
                    e.printStackTrace();
                }
                handler.postDelayed(this, 500);
            }
        }, 100);
    }
    private void SetTime(){
        SimpleDateFormat dinhdanggio = new SimpleDateFormat("mm:ss");
        textViewTimeLast.setText(dinhdanggio.format(mediaPlayer.getDuration()));
        //gan max cua seekBar = mediaPlayer.getDuration()
        seekBar.setMax(mediaPlayer.getDuration());
    }
    private void KhoiTaoMediaPlayer() {
        try {
            mediaPlayer = MediaPlayer.create(SongsActivity.this, arraySong.get(position).getFile());
            textViewTitle.setText(arraySong.get(position).getTitle());
            imageSong.setImageResource(arraySong.get(position).getImg());

        } catch (Exception e) {
            // Handle the exception, for example, by logging it or displaying an error message.
            e.printStackTrace();
        }
    }

    private void AddSong() {
        arraySong = new ArrayList<>();
        try {
            arraySong.add(new Song("Nếu lúc đó", R.raw.neulucdo, R.drawable.neulucdo));
            arraySong.add(new Song("Dạy tôi cách yêu", R.raw.daytoicachyeu, R.drawable.daytoicachyeu));
            arraySong.add(new Song("Tình yêu có nghĩa là gì", R.raw.tinhyeuconghialagi, R.drawable.ai));
            arraySong.add(new Song("id 072019", R.raw.wn, R.drawable.wn));
            arraySong.add(new Song("3107", R.raw.wnduonggnau, R.drawable.nau));
        } catch (Exception e) {
            // Handle the exception, for example, by logging it or displaying an error message.
            e.printStackTrace();
        }
    }
    public void doButtonList(View v) {
        Intent it = new Intent(this, ListActivity.class);
        startActivity(it);
    }
}