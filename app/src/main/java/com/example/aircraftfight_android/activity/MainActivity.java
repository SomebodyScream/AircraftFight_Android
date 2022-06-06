package com.example.aircraftfight_android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.fragment.ChoiceFragment;
import com.example.aircraftfight_android.fragment.ConnectFragment;
import com.example.aircraftfight_android.fragment.DifficultyFragment;
import com.example.aircraftfight_android.game.application.ImageManager;
import com.example.aircraftfight_android.helper.AuthenticationHelper;
import com.example.aircraftfight_android.helper.MusicServiceHelper;
import com.example.aircraftfight_android.helper.SharedPreferenceHelper;

public class MainActivity extends BaseActivity {

    public static int HEIGHT;
    public static int WIDTH;
    @SuppressLint("StaticFieldLeak")
    public static MusicServiceHelper musicHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Music service start
        musicHelper = new MusicServiceHelper(this);

        SharedPreferenceHelper helper = new SharedPreferenceHelper(this, SettingActivity.SP_DATABASE_SETTING);
        musicHelper.setBgmOn((Boolean) helper.readProperty(SettingActivity.SPLABEL_SETTING_BGM, SharedPreferenceHelper.READ_MODE_BOOLEAN));
        musicHelper.setSoundEffectOn((Boolean) helper.readProperty(SettingActivity.SPLABEL_SETTING_SOUND_EFFECT, SharedPreferenceHelper.READ_MODE_BOOLEAN));

        AuthenticationHelper authenticationHelper = new AuthenticationHelper(this);
        authenticationHelper.checkLogin();

        // 获取屏幕尺寸(包含状态栏，导航栏)
        Point outSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(outSize);
        WIDTH = outSize.x;
        HEIGHT = outSize.y;

        // 初始化ImageManager
        ImageManager.initial(getResources());

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Fragment fragment = new ChoiceFragment();
        initialFragment(fragment);

        ImageView imageView = findViewById(R.id.main_activity_background);
        Glide.with(this).load(R.drawable.main_background_dynamic).into(imageView);
    }

    public void replaceFragmentDifficulty(){
        replaceFragment(new DifficultyFragment());
    }

    public void startGameActivity(String difficulty){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
    }

    public void startChangeActivity(){
        Intent intent = new Intent(this, ChangeAcvitity.class);
        startActivity(intent);
    }
    public void startShoppingActivity(){
        Intent intent = new Intent(this, ShoppingActivity.class);
        startActivity(intent);
    }

    public void startAuthenticationActivity(){
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
    }

    public void replaceFragmentMulti(){
        replaceFragment(new ConnectFragment());
    }

    public void replaceFragmentChoice(){
        replaceFragment(new ChoiceFragment());
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
//        transaction.addToBackStack(null);
        transaction.commit();
    }

}