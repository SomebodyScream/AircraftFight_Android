package com.example.aircraftfight_android.fragment;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.application.Game;

import java.util.zip.Inflater;

public class DifficultyFragment extends Fragment
{
    public DifficultyFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_difficulty, container, false);

        MainActivity activity = (MainActivity) getActivity();

        ImageButton buttonEasy = (ImageButton) view.findViewById(R.id.button_easy);
        ImageButton buttonNormal = (ImageButton) view.findViewById(R.id.button_normal);
        ImageButton buttonHard = (ImageButton) view.findViewById(R.id.button_hard);

        buttonEasy.setOnClickListener(v -> {
            if(activity != null){
                activity.startGameActivity(Game.EASY);
            }
        });

        buttonNormal.setOnClickListener(v -> {
            if(activity != null){
                activity.startGameActivity(Game.NORMAL);
            }
        });

        buttonHard.setOnClickListener(v -> {
            if(activity != null){
                activity.startGameActivity(Game.HARD);
            }
        });

        return view;
    }
}
