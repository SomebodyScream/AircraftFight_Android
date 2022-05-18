package com.example.aircraftfight_android.activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class GameActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("GameActivity", "onCreated start");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        FrameLayout layout = (FrameLayout) getLayoutInflater().inflate(R.layout.activity_game, null);

//        layout.addView(new SurfaceView(this));

        Game game = new EasyGame(this);
        game.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.addView(game);

        setContentView(layout);

        Log.d("GameActivity", "onCreated Done");
    }
}