package com.example.aircraftfight_android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.fragment.ChoiceFragment;
import com.example.aircraftfight_android.fragment.ConnectFragment;
import com.example.aircraftfight_android.fragment.DifficultyFragment;
import com.example.aircraftfight_android.game.application.Game;
import com.example.aircraftfight_android.game.application.ImageManager;
import com.example.aircraftfight_android.helper.AuthenticationHelper;
import com.example.aircraftfight_android.helper.MusicServiceHelper;
import com.example.aircraftfight_android.helper.SharedPreferenceHelper;

public class MainActivity extends BaseActivity {

    public static int HEIGHT;
    public static int WIDTH;
    @SuppressLint("StaticFieldLeak")
    public static MusicServiceHelper musicHelper;
    private Fragment fragment;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Customized helpers initialize
        musicHelper = new MusicServiceHelper(this);
        SharedPreferenceHelper helper = new SharedPreferenceHelper(this, SettingActivity.SP_DATABASE_SETTING);
        AuthenticationHelper authenticationHelper = new AuthenticationHelper(this);

        // Music service start
        musicHelper.setBgmOn((Boolean) helper.readProperty(SettingActivity.SP_LABEL_SETTING_BGM, SharedPreferenceHelper.READ_MODE_BOOLEAN));
        musicHelper.setSoundEffectOn((Boolean) helper.readProperty(SettingActivity.SP_LABEL_SETTING_SOUND_EFFECT, SharedPreferenceHelper.READ_MODE_BOOLEAN));

        // Authentication check
        if (authenticationHelper.isLogin()){
            authenticationHelper.checkLogin();
        }

        // Get window size
        Point outSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(outSize);
        WIDTH = outSize.x;
        HEIGHT = outSize.y;

        // Initial image manager
        ImageManager.initial(getResources());

        // UI initial
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fragment = new ChoiceFragment();
        initialFragment(fragment);
        playBackground();
    }

    @Override
    public void onResume() {
        if (fragment!=null){
            reloadFragment();
        }
        playBackground();
        super.onResume();
    }

    public void replaceFragmentDifficulty(){
        fragment = new DifficultyFragment();
        replaceFragment(fragment);
    }

    public void startGameActivity(String gameMode){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("gameMode", gameMode);
        startActivity(intent);
    }

    public void startChangeActivity(){
        Intent intent = new Intent(this, ChangeActivity.class);
        startActivity(intent);
    }

    public void startRecordActivity(int score){
        Intent intent = new Intent(this, RecordActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("gameMode", Game.ONLINE);
        startActivity(intent);
    }

    public void startRecordActivity(){
        startRecordActivity(-1);
    }

    public void startAuthenticationActivity(){
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
    }

    public void replaceFragmentMulti(){
        fragment = new ConnectFragment();
        replaceFragment(fragment);
    }

    public void replaceFragmentChoice(){
        fragment = new ChoiceFragment();
        replaceFragment(fragment);
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
        transaction.commit();
    }

    private void reloadFragment(){
        Class<? extends Fragment> fragmentClass = fragment.getClass();
        try {
            fragment = fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        replaceFragment(fragment);
    }

    private void playBackground(){
        VideoView mVideoView = findViewById(R.id.main_background);
        mVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/raw/bg"));
        mVideoView.start();
        mVideoView.setOnPreparedListener(mp -> mp.setLooping(true));
    }
}