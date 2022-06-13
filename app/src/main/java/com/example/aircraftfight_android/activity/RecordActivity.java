package com.example.aircraftfight_android.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.fragment.MultiRecordFragment;
import com.example.aircraftfight_android.fragment.SingleRecordFragment;
import com.example.aircraftfight_android.game.application.Game;

public class RecordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_record);

        // 获取GameActivity传递的数据
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", -1);
        String gameMode = intent.getStringExtra("gameMode");

        TextView textView = findViewById(R.id.text_mode);
        textView.setText("Current choose list : mode "+gameMode);
        textView.setTypeface(Typeface.createFromAsset(this.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));

        ImageButton backButton = findViewById(R.id.button_back_record);
        Button button_online = findViewById(R.id.mode_online);
        Button button_easy = findViewById(R.id.mode_easy);
        Button button_normal = findViewById(R.id.mode_normal);
        Button button_hard = findViewById(R.id.mode_hard);


        backButton.setBackgroundColor(Color.TRANSPARENT);

        playBackground();

        if (gameMode.equals(Game.ONLINE)){
            replaceFragment(new MultiRecordFragment());
        }else{
            replaceFragment(new SingleRecordFragment(score, gameMode));
        }

        backButton.setOnClickListener(v -> finish());

        button_online.setOnClickListener(v -> {
            textView.setText("Current choose list : mode "+Game.ONLINE);
            replaceFragment(new MultiRecordFragment());
        });

        button_easy.setOnClickListener(v -> {
            textView.setText("Current choose list : mode "+Game.EASY);
            replaceFragment(new SingleRecordFragment(score, Game.EASY));
        });
        button_normal.setOnClickListener(v -> {
            textView.setText("Current choose list : mode "+Game.NORMAL);
            replaceFragment(new SingleRecordFragment(score, Game.NORMAL));
        });
        button_hard.setOnClickListener(v -> {
            textView.setText("Current choose list : mode "+Game.HARD);
            replaceFragment(new SingleRecordFragment(score, Game.HARD));
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.record_activity_fragment_content,fragment);
        transaction.commit();
    }

    private void playBackground(){
        VideoView mVideoView = findViewById(R.id.record_background);
        mVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/raw/bg"));
        mVideoView.start();
        mVideoView.setOnPreparedListener(mp -> mp.setLooping(true));
    }

}