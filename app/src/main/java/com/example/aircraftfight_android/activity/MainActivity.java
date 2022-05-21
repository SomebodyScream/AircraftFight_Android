package com.example.aircraftfight_android.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.fragment.ChoiceFragment;
import com.example.aircraftfight_android.fragment.ConnectFragment;
import com.example.aircraftfight_android.fragment.DifficultyFragment;
import com.example.aircraftfight_android.game.application.Game;
import com.example.aircraftfight_android.game.application.ImageManager;
import com.example.aircraftfight_android.helper.MusicServiceHelper;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    public static int HEIGHT;
    public static int WIDTH;
    public static MusicServiceHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Music service start
        helper = new MusicServiceHelper(this);

        // 获取屏幕尺寸
        Display display = getWindowManager().getDefaultDisplay();

        Point point = new Point();
        display.getSize(point);
        WIDTH = point.x;
        HEIGHT = point.y;

        // 初始化ImageManager
        ImageManager.initial(getResources());

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Fragment fragment = new ChoiceFragment();
        initialFragment(fragment);

    }

    public void replaceFragmentDifficulty(){
        replaceFragment(new DifficultyFragment());
    }

    public void startGameActivity(String difficulty){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }

    public void startRecordActivity(){
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }

    public void replaceFragmentMulti(){
        replaceFragment(new ConnectFragment());
    }

    public void startSettingActivity(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    private void initialFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_activity_fragment_content,fragment);
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_activity_fragment_content,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}