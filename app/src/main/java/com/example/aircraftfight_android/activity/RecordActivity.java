package com.example.aircraftfight_android.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;

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

        ImageButton backButton = findViewById(R.id.button_back_record);

        backButton.setBackgroundColor(Color.TRANSPARENT);

        if (gameMode.equals(Game.ONLINE)){
            replaceFragment(new MultiRecordFragment());
        }else{
            replaceFragment(new SingleRecordFragment(score, gameMode));
        }

        backButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.record_activity_fragment_content,fragment);
        transaction.commit();
    }

}