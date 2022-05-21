package com.example.aircraftfight_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.game.application.EasyGame;
import com.example.aircraftfight_android.game.application.Game;
import com.example.aircraftfight_android.game.application.GameOverCallback;
import com.example.aircraftfight_android.game.application.HardGame;
import com.example.aircraftfight_android.game.application.NormalGame;
import com.example.aircraftfight_android.helper.SharedPreferenceHelper;

public class GameActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FrameLayout layout = (FrameLayout) getLayoutInflater().inflate(R.layout.activity_game, null);

        GameOverCallback callback = () -> {
//            SharedPreferenceHelper sphelper = new SharedPreferenceHelper(this, "SingleScore");
//            sphelper.writeProperty("");

            Intent intent = new Intent(this, RecordActivity.class);
            startActivity(intent);

            this.finish();
        };

        String difficulty = getIntent().getStringExtra("difficulty");

        Game game;
        if(difficulty.equals(Game.EASY)){
            game = new EasyGame(this, callback);
        }
        else if(difficulty.equals(Game.NORMAL)){
            game = new NormalGame(this, callback);
        }
        else{
            game = new HardGame(this, callback);
        }

        game.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.addView(game);

        setContentView(layout);
    }
}