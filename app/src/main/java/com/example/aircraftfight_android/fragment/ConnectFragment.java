package com.example.aircraftfight_android.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.application.Game;
import com.example.aircraftfight_android.helper.AuthenticationHelper;
import com.example.aircraftfight_android.helper.HttpHelper;
import com.example.aircraftfight_android.manager.HeroManager;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Response;


public class ConnectFragment extends Fragment implements okhttp3.Callback{

    private MainActivity activity;
    private ImageView imageViewWaiting;
    private ImageButton buttonConnect;

    private ScheduledExecutorService executorService = null;
    private boolean connectFailureFlag = false;
    private boolean isMatched = false;
    private boolean isConnecting = false;

    public ConnectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect, container, false);

        ImageButton buttonBack = (ImageButton) view.findViewById(R.id.button_back_connect);
        buttonConnect = view.findViewById(R.id.button_start_connect);
        ImageButton buttonChangeHero = view.findViewById(R.id.button_change_hero_connect);
        imageViewWaiting = view.findViewById(R.id.image_waiting);

        TextView somebodyView = view.findViewById(R.id.somebody_icecream);
        somebodyView.setSelected(true);
        somebodyView.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "AveriaSerifLibre-Italic-4.ttf"));

        ImageView imageChooseHero = view.findViewById(R.id.image_choose_hero_connect);
        ImageView imageChooseHeroCG = view.findViewById(R.id.image_choose_hero_cg_connect);
        TextView textHeroInfo = view.findViewById(R.id.text_hero_info_connect);
        textHeroInfo.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
        textHeroInfo.setText(HeroManager.getHeroManager(activity).getHeroInfo());
        Glide.with(activity).load(HeroManager.getHeroManager(activity).drawHeroGif()).into(imageChooseHero);
        Glide.with(activity).load(HeroManager.getHeroManager(activity).drawHeroCg()).into(imageChooseHeroCG);


        buttonBack.setBackgroundColor(Color.TRANSPARENT);
        buttonBack.setOnClickListener(v -> {
            if (activity != null){
                activity.replaceFragmentChoice();
            }
        });

        buttonChangeHero.setOnClickListener(v -> {
            activity.startChangeActivity();
        });

        Glide.with(activity).load(R.drawable.icon_load).into(imageViewWaiting);
        imageViewWaiting.setVisibility(View.INVISIBLE);

        buttonConnect.setOnClickListener(v -> {
            startConnect();
        });

        return view;
    }

    private void startConnect()
    {
        AuthenticationHelper authHelper = new AuthenticationHelper(activity);

        if(!authHelper.isLogin()) // user didn't login
        {
            activity.startAuthenticationActivity();
        }
        else
        {
            connectFailureFlag = false;
            isMatched = false;

            imageViewWaiting.setVisibility(View.VISIBLE);
            executorService = new ScheduledThreadPoolExecutor(1,
                    new BasicThreadFactory.Builder().namingPattern("match-request-%d").daemon(true).build());

            Runnable requestTask = () -> {
                String url = HttpHelper.IP + "/match?" + "user=" + authHelper.getUsername();
                HttpHelper.sendGetRequest(url, this);
            };

            executorService.scheduleWithFixedDelay(requestTask, 0, 1000, TimeUnit.MILLISECONDS);
        }
    }

    private void cancelConnect()
    {
        // TODO
    }

    /**
     * OkHttp callback
     */
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        executorService.shutdown();
        if(activity != null && !connectFailureFlag){
            imageViewWaiting.setVisibility(View.INVISIBLE);
            activity.runOnUiThread(()->{
                Toast.makeText(activity, "Failed in connection. Please try again.", Toast.LENGTH_SHORT).show();
            });
            connectFailureFlag = true;
        }
    }

    /**
     * OkHttp callback
     */
    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if(isMatched) {
            return;
        }

        String responseJson = response.body().string();
        ServerResponseData responseData = new ServerResponseData(responseJson);

        if(responseData.isMatched())
        {
            isMatched = true;
            executorService.shutdown();

            if(activity != null){
                activity.runOnUiThread(()->{
                    imageViewWaiting.setVisibility(View.INVISIBLE);
                    activity.startGameActivity(Game.ONLINE);
                });
            }
        }
    }

    /**
     * Inner class for managing server response data
     */
    private class ServerResponseData
    {
        private String roomId;
        private boolean match_state;

        public ServerResponseData(String responseJson)
        {
            parseResponseJson(responseJson);
        }

        public String getRoomId() {
            return roomId;
        }

        public boolean isMatched() {
            return match_state;
        }

        public void parseResponseJson(String jsonData)
        {
            try{
                JSONObject jsonObject = new JSONObject(jsonData);

                roomId = jsonObject.getString( "roomId" );
                match_state = jsonObject.getBoolean( "match_state");
            }
            catch(Exception e){
                Log.e( "ConnectFragment", e.toString()) ;
                e.printStackTrace();
            }
        }
    }
}