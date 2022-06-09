package com.example.aircraftfight_android.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.application.Game;
import com.example.aircraftfight_android.manager.HeroManager;

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
        assert activity != null;

        ImageButton buttonEasy = view.findViewById(R.id.button_easy);
        ImageButton buttonNormal = view.findViewById(R.id.button_normal);
        ImageButton buttonHard = view.findViewById(R.id.button_hard);
        ImageButton buttonBack = view.findViewById(R.id.button_back_choice);
        ImageButton buttonChangeHero = view.findViewById(R.id.button_change_hero_1);

        ImageView imageChooseHero = view.findViewById(R.id.image_choose_hero);
        ImageView imageChooseHeroCG = view.findViewById(R.id.image_choose_hero_cg);
        TextView textHeroInfo = view.findViewById(R.id.text_hero_info);
        textHeroInfo.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
        textHeroInfo.setText(HeroManager.getHeroManager(activity).getHeroInfo());
        Glide.with(activity).load(HeroManager.getHeroManager(activity).drawHeroGif()).into(imageChooseHero);
        Glide.with(activity).load(HeroManager.getHeroManager(activity).drawHeroCg()).into(imageChooseHeroCG);

        buttonBack.setBackgroundColor(Color.TRANSPARENT);

        buttonEasy.setOnClickListener(v -> {
            activity.startGameActivity(Game.EASY);
        });

        buttonNormal.setOnClickListener(v -> {
            activity.startGameActivity(Game.NORMAL);
        });

        buttonHard.setOnClickListener(v -> {
            activity.startGameActivity(Game.HARD);
        });

        buttonBack.setOnClickListener(v -> {
            activity.replaceFragmentChoice();
        });

        buttonChangeHero.setOnClickListener(v -> {
            activity.startChangeActivity();
        });

        return view;
    }
}
