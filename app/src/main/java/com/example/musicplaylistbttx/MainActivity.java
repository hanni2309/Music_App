package com.example.musicplaylistbttx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.musicplayer.ListActivity;
import com.example.musicplayer.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void doList(View v){
        Intent it = new Intent(this, ListActivity.class);
        startActivity(it);
    }
}