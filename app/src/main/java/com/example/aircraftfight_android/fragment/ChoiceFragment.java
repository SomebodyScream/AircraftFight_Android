package com.example.aircraftfight_android.fragment;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.helper.AuthenticationHelper;
import com.example.aircraftfight_android.manager.HeroManager;

public class ChoiceFragment extends Fragment {

    public ChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
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

        ImageView imageView = view.findViewById(R.id.image_show_hero);
        assert activity != null;
        Glide.with(activity).load(HeroManager.getHeroManager(activity).drawHeroGif()).into(imageView);
        TextView textVersion = view.findViewById(R.id.text_app_v);
        TextView textUsername = view.findViewById(R.id.text_main_page_user);
        TextView textPoint = view.findViewById(R.id.text_main_page_point);

        textUsername.setTypeface(Typeface.createFromAsset(activity.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
        textPoint.setTypeface(Typeface.createFromAsset(activity.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));

        PackageManager pm = activity.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(activity.getPackageName(), 0);
            textVersion.setText("Build version : "+ packageInfo.getLongVersionCode());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        AuthenticationHelper authenticationHelper = new AuthenticationHelper(activity);
        if (authenticationHelper.isLogin()){
            textUsername.setText("USER : "+authenticationHelper.getUsername());
            textPoint.setText("PTS : "+"1000");
            //TODO:point
        }

        // Listeners set
        buttonMultiplayer.setOnClickListener(v -> activity.replaceFragmentMulti());

        buttonSetting.setOnClickListener(v -> activity.startSettingActivity());

        buttonSinglePlayer.setOnClickListener(v -> activity.replaceFragmentDifficulty());

        buttonShopping.setOnClickListener(v -> activity.startShoppingActivity());

        buttonChange.setOnClickListener(v -> activity.startChangeActivity());
        return view;
    }
}