package com.example.aircraftfight_android.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.manager.HeroManager;

public class ChangeActivity extends BaseActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        Button buttonReimu = findViewById(R.id.choose_reimu);
        Button buttonMarisa = findViewById(R.id.choose_marisa);
        Button buttonYoumu = findViewById(R.id.choose_youmu);

        ImageButton buttonBack  = findViewById(R.id.button_back_change);

        buttonBack.setBackgroundColor(Color.TRANSPARENT);

        playBackground();

        switch (HeroManager.getHeroManager(this).getSelfId()){
            case HeroManager.HERO_ID_REIMU:{
                buttonReimu.setText("CHOSEN");
                buttonReimu.setTextColor(Color.rgb(255,215,0));
                break;
            }
            case HeroManager.HERO_ID_MARISA:{
                buttonMarisa.setText("CHOSEN");
                buttonMarisa.setTextColor(Color.rgb(255,215,0));
                break;
            }
            case HeroManager.HERO_ID_YOUMU:{
                buttonYoumu.setText("CHOSEN");
                buttonYoumu.setTextColor(Color.rgb(255,215,0));
                break;
            }
        }

        buttonReimu.setOnClickListener(v -> {
            buttonReimu.setText("CHOSEN");
            buttonMarisa.setText("CHOOSE");
            buttonYoumu.setText("CHOOSE");
            buttonReimu.setTextColor(Color.rgb(255,215,0));
            buttonMarisa.setTextColor(Color.rgb(255,255,255));
            buttonYoumu.setTextColor(Color.rgb(255,255,255));
            HeroManager.getHeroManager(this).setSelfId(HeroManager.HERO_ID_REIMU);
        });

        buttonMarisa.setOnClickListener(v -> {
            buttonReimu.setText("CHOOSE");
            buttonMarisa.setText("CHOSEN");
            buttonYoumu.setText("CHOOSE");
            buttonReimu.setTextColor(Color.rgb(255,255,255));
            buttonMarisa.setTextColor(Color.rgb(255,215,0));
            buttonYoumu.setTextColor(Color.rgb(255,255,255));
            HeroManager.getHeroManager(this).setSelfId(HeroManager.HERO_ID_MARISA);
        });

        buttonYoumu.setOnClickListener(v -> {
            buttonReimu.setText("CHOOSE");
            buttonMarisa.setText("CHOOSE");
            buttonYoumu.setText("CHOSEN");
            buttonReimu.setTextColor(Color.rgb(255,255,255));
            buttonMarisa.setTextColor(Color.rgb(255,255,255));
            buttonYoumu.setTextColor(Color.rgb(255,215,0));
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