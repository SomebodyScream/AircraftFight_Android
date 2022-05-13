package com.example.aircraftfight_android.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Fragment fragment = new ChoiceFragment();
        initialFragment(fragment);

    }

    public void replaceFragmentDifficulty(){
        replaceFragment(new DifficultyFragment());
    }

    public void startGameActivity(){
        Intent intent = new Intent(this, GameActivity.class);
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