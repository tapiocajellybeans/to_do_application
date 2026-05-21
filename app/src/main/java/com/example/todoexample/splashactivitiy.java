package com.example.todoexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class splashactivitiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        ImageView bongo1 = findViewById(R.id.bongo1);
        final MediaPlayer cat_meow = MediaPlayer.create(this, R.raw.sounds_meow);
        final MediaPlayer cat_bongo1 = MediaPlayer.create(this, R.raw.sounds_bongo0);
        final MediaPlayer cat_bongo2 = MediaPlayer.create(this, R.raw.sounds_bongo1);

        final Intent i = new Intent(splashactivitiy.this, MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bongo1.setVisibility(View.GONE);
                cat_bongo1.start();
                cat_bongo2.start();
            }
        }, 500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bongo1.setVisibility(View.VISIBLE);
                cat_meow.start();
            }
        }, 750);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        }, 1500); //after showing splash screen for 1 second, go back to main activity
    }
}