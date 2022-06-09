package com.example.aircraftfight_android.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.manager.HeroManager;

public class ChangeActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        TextView textReimu = findViewById(R.id.text_reimu);
        TextView textMarisa = findViewById(R.id.text_marisa);
        TextView textYoumu = findViewById(R.id.text_youmu);

        Button buttonReimu = findViewById(R.id.choose_reimu);
        Button buttonMarisa = findViewById(R.id.choose_marisa);
        Button buttonYoumu = findViewById(R.id.choose_youmu);

        ImageButton buttonBack  = findViewById(R.id.button_back_change);


        textReimu.setTypeface(Typeface.createFromAsset(getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
        textMarisa.setTypeface(Typeface.createFromAsset(getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
        textYoumu.setTypeface(Typeface.createFromAsset(getAssets(), "AveriaSerifLibre-Italic-4.ttf"));

        buttonReimu.setTypeface(Typeface.createFromAsset(getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
        buttonMarisa.setTypeface(Typeface.createFromAsset(getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
        buttonYoumu.setTypeface(Typeface.createFromAsset(getAssets(), "AveriaSerifLibre-Italic-4.ttf"));

        buttonBack.setBackgroundColor(Color.TRANSPARENT);

        playBackground();

        switch (HeroManager.getHeroManager(this).getSelfId()){
            case HeroManager.HERO_ID_REIMU:{
                buttonReimu.setText("CHOSEN");
                break;
            }
            case HeroManager.HERO_ID_MARISA:{
                buttonMarisa.setText("CHOSEN");
                break;
            }
            case HeroManager.HERO_ID_YOUMU:{
                buttonYoumu.setText("CHOSEN");
                break;
            }
        }

        buttonReimu.setOnClickListener(v -> {
            buttonReimu.setText("CHOSEN");
            buttonMarisa.setText("CHOOSE");
            buttonYoumu.setText("CHOOSE");
            HeroManager.getHeroManager(this).setSelfId(HeroManager.HERO_ID_REIMU);
        });

        buttonMarisa.setOnClickListener(v -> {
            buttonReimu.setText("CHOOSE");
            buttonMarisa.setText("CHOSEN");
            buttonYoumu.setText("CHOOSE");
            HeroManager.getHeroManager(this).setSelfId(HeroManager.HERO_ID_MARISA);
        });

        buttonYoumu.setOnClickListener(v -> {
            buttonReimu.setText("CHOOSE");
            buttonMarisa.setText("CHOOSE");
            buttonYoumu.setText("CHOSEN");
            HeroManager.getHeroManager(this).setSelfId(HeroManager.HERO_ID_YOUMU);
        });

        buttonBack.setOnClickListener(v -> finish());
    }


    private void playBackground(){
        VideoView mVideoView = findViewById(R.id.change_background);
        mVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/raw/bg"));
        mVideoView.start();
        mVideoView.setOnPreparedListener(mp -> mp.setLooping(true));
    }
}