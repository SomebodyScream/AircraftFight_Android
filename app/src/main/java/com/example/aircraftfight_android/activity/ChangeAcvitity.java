package com.example.aircraftfight_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftfight_android.R;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;

public class ChangeAcvitity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        ImageButton buttonBack  = findViewById(R.id.button_back_change);
        buttonBack.setBackgroundColor(Color.TRANSPARENT);

        buttonBack.setOnClickListener(v -> {
            finish();
        });
    }
}