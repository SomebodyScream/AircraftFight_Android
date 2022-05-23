package com.example.aircraftfight_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.game.application.EasyGame;
import com.example.aircraftfight_android.game.application.Game;
import com.example.aircraftfight_android.game.application.GameOverCallback;
import com.example.aircraftfight_android.game.application.HardGame;
import com.example.aircraftfight_android.game.application.NormalGame;
import com.example.aircraftfight_android.helper.SharedPreferenceHelper;

public class GameActivity extends BaseActivity
{
    private Game game;
    private ImageButton buttonPause;
    private ImageButton buttonResume;
    private FrameLayout grayMask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        buttonPause = findViewById(R.id.button_pause);
        buttonResume = findViewById(R.id.button_resume);
        grayMask = findViewById(R.id.gray_mask);

        grayMask.setVisibility(View.GONE);

        initGameView();
        initPauseAndResumeButton();
    }

    private void pauseGame()
    {
        game.pauseGame();
        buttonResume.setVisibility(View.VISIBLE);
        grayMask.setVisibility(View.VISIBLE);
    }

    private void resumeGame()
    {
        game.resumeGame();
        buttonResume.setVisibility(View.GONE);
        grayMask.setVisibility(View.GONE);
    }

    private void initPauseAndResumeButton()
    {
        buttonPause.setBackgroundColor(Color.TRANSPARENT);
        buttonResume.setBackgroundColor(Color.TRANSPARENT);

        buttonResume.setVisibility(View.GONE);

        buttonPause.setOnClickListener(v -> this.pauseGame());
        buttonResume.setOnClickListener(v -> this.resumeGame());
    }

    private void initGameView()
    {
        FrameLayout container = findViewById(R.id.game_container);

        GameOverCallback callback = (score, difficulty) -> {
            Intent intent = new Intent(this, RecordActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("difficulty", difficulty);
            startActivity(intent);

            this.finish();
        };

        String difficulty = getIntent().getStringExtra("difficulty");

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
        container.addView(game);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(game.isPause()) {
                finish();
            }
            else {
                pauseGame();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        MainActivity.musicHelper.pauseBackgroundMusic();
        MainActivity.musicHelper.stopBossMusic();
        super.onDestroy();
    }
}