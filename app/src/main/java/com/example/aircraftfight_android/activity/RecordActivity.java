package com.example.aircraftfight_android.activity;

import android.annotation.SuppressLint;
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
import com.example.aircraftfight_android.helper.AuthenticationHelper;
import com.example.aircraftfight_android.helper.SingleRecordHelper;

public class RecordActivity extends BaseActivity {
    private String curMode;
    private TextView textView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_record);

        // get data
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", -1);
        String gameMode = intent.getStringExtra("gameMode");

        // add new record
        addNewRecord(gameMode, score);

        textView = findViewById(R.id.text_mode);
        textView.setText("Current choose list : " + gameMode);
        textView.setTypeface(Typeface.createFromAsset(this.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));

        ImageButton backButton = findViewById(R.id.button_back_record);
        Button button_online = findViewById(R.id.mode_online);
        Button button_easy = findViewById(R.id.mode_easy);
        Button button_normal = findViewById(R.id.mode_normal);
        Button button_hard = findViewById(R.id.mode_hard);


        backButton.setBackgroundColor(Color.TRANSPARENT);

        playBackground();

        curMode = gameMode;
        if (gameMode.equals(Game.ONLINE)){
            replaceFragment(new MultiRecordFragment());
        }else{
            replaceFragment(new SingleRecordFragment(gameMode));
        }

        backButton.setOnClickListener(v -> finish());

        button_online.setOnClickListener(v -> changeRecordMode(Game.ONLINE));
        button_easy.setOnClickListener(v -> changeRecordMode(Game.EASY));
        button_normal.setOnClickListener(v -> changeRecordMode(Game.NORMAL));
        button_hard.setOnClickListener(v -> changeRecordMode(Game.HARD));

    }

    @Override
    public void onResume() {
        playBackground();
        super.onResume();
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

    @SuppressLint("SetTextI18n")
    private void changeRecordMode(String gameMode)
    {
        if(!gameMode.equals(curMode))
        {
            curMode = gameMode;
            textView.setText("Current choose list : " + gameMode);
            if(gameMode.equals(Game.ONLINE)){
                replaceFragment(new MultiRecordFragment());
            }
            else{
                replaceFragment(new SingleRecordFragment(gameMode));
            }
        }
    }

    private void addNewRecord(String gameMode, int score)
    {
        if(!gameMode.equals(Game.ONLINE) && score != -1)
        {
            // Get user name
            String playerName = "Anonymous";
            AuthenticationHelper authHelper = new AuthenticationHelper(this);
            if(authHelper.isLogin()){
                playerName = authHelper.getUsername();
            }

            // add record
            SingleRecordHelper recordHelper = new SingleRecordHelper(this, gameMode);
            recordHelper.addRecord(playerName, score);
        }
    }

}