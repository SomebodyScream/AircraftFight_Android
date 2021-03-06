package com.example.aircraftfight_android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.helper.SharedPreferenceHelper;
import com.google.android.material.card.MaterialCardView;

public class SettingActivity extends BaseActivity {

    private SharedPreferenceHelper helper;
    public static String SP_DATABASE_SETTING = "settingConf";
    public static String SP_LABEL_SETTING_BGM = "background_music";
    public static String SP_LABEL_SETTING_SOUND_EFFECT = "sound_effect";
    public static String SP_LABEL_SETTING_OFFLINE_RECORD = "offline_record";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Variable initialize
        helper = new SharedPreferenceHelper(this, SP_DATABASE_SETTING);

        //Instance declare
        ImageButton buttonClearRecord = findViewById(R.id.button_clear_record);
        ImageButton buttonSetAccount = findViewById(R.id.button_set_account);
        ImageButton buttonBack = findViewById(R.id.button_back_setting);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchBGM = findViewById(R.id.switch_bgm);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchSoundEffect = findViewById(R.id.switch_soundEffect);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchOfflineRecord = findViewById(R.id.switch_offline_record);
        MaterialCardView cardGetStarted = findViewById(R.id.card_get_started);
        MaterialCardView cardVisitGit = findViewById(R.id.card_visit_git);

        //UI config
        buttonBack.setBackgroundColor(Color.TRANSPARENT);
        buttonClearRecord.setBackgroundColor(Color.TRANSPARENT);
        buttonSetAccount.setBackgroundColor(Color.TRANSPARENT);
        switchBGM.setChecked((Boolean) helper.readProperty(SP_LABEL_SETTING_BGM,
                SharedPreferenceHelper.READ_MODE_BOOLEAN));
        switchSoundEffect.setChecked((Boolean) helper.readProperty(SP_LABEL_SETTING_SOUND_EFFECT,
                SharedPreferenceHelper.READ_MODE_BOOLEAN));
        switchOfflineRecord.setChecked((Boolean) helper.readProperty(SP_LABEL_SETTING_OFFLINE_RECORD,
                SharedPreferenceHelper.READ_MODE_BOOLEAN));

        //Listener set
        cardGetStarted.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://github.com/aucki6144/AircraftFight_Android/blob/main/README.md");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        cardVisitGit.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://github.com/aucki6144/AircraftFight_Android");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        buttonSetAccount.setOnClickListener(v->{
            Intent intent = new Intent(this, AuthenticationActivity.class);
            startActivity(intent);
        });

        switchOfflineRecord.setOnCheckedChangeListener((buttonView, isChecked)
                -> helper.writeProperty(SP_LABEL_SETTING_OFFLINE_RECORD,isChecked));

        switchBGM.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MainActivity.musicHelper.setBgmOn(isChecked);
            helper.writeProperty(SP_LABEL_SETTING_BGM, isChecked);
        });

        switchSoundEffect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MainActivity.musicHelper.setSoundEffectOn(isChecked);
            helper.writeProperty(SP_LABEL_SETTING_SOUND_EFFECT, isChecked);
        });

        buttonBack.setOnClickListener(v -> finish());

        buttonClearRecord.setOnClickListener(v -> {
            helper.clearProperty("Records");
            Toast.makeText(this, "Records cleared!", Toast.LENGTH_SHORT).show();
        });

    }
}