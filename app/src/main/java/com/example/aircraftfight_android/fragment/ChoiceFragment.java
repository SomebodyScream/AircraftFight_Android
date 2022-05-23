package com.example.aircraftfight_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.helper.DrawHeroHelper;


public class ChoiceFragment extends Fragment {

    public ChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choice, container, false);
        MainActivity activity = (MainActivity) getActivity();

        // View instance initialize
        ImageButton buttonMultiplayer = view.findViewById(R.id.button_multiplayer);
        ImageButton buttonSinglePlayer = view.findViewById(R.id.button_singleplayer);
        ImageButton buttonSetting = view.findViewById(R.id.button_setting);
        ImageButton buttonShopping = view.findViewById(R.id.button_store);
        ImageButton buttonChange = view.findViewById(R.id.button_change_hero);

        DrawHeroHelper drawHeroHelper = new DrawHeroHelper(this.getActivity());

        ImageView imageView = view.findViewById(R.id.image_show_hero);
        Glide.with(this.getActivity()).load(drawHeroHelper.drawHeroGif()).into(imageView);

        // Listeners set
        buttonMultiplayer.setOnClickListener(v -> {
            if (activity!=null){
                activity.replaceFragmentMulti();
            }
        });

        buttonSetting.setOnClickListener(v -> {
            if (activity!=null){
                activity.startSettingActivity();
            }
        });

        buttonSinglePlayer.setOnClickListener(v -> {
            if(activity!=null){
                activity.replaceFragmentDifficulty();
            }
        });

        buttonShopping.setOnClickListener(v -> {
            if (activity!=null){
                activity.startShoppingActivity();
            }
        });

        buttonChange.setOnClickListener(v -> {
            if (activity!=null){
                activity.startChangeActivity();
            }
        });
        return view;
    }
}