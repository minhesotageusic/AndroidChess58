package com.example.chess58;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    Button playBtn, playbackBtn;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        setContentView(R.layout.activity_main);
        playBtn = findViewById(R.id.playBtn);
        playbackBtn = findViewById(R.id.playbackBtn);
    }
    public void OpenChessActivity(View view){
        Intent intent = new Intent(this, ChessActivity.class);
        startActivity(intent);
    }
    public void OpenPlaybackActivity(View view){
        Intent intent = new Intent(this, Playback.class);
        startActivity(intent);
    }
}