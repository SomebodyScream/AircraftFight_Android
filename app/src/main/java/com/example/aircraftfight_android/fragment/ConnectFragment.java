package com.example.aircraftfight_android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.application.Game;
import com.example.aircraftfight_android.helper.AuthenticationHelper;
import com.example.aircraftfight_android.helper.HttpHelper;

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

    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("match-request-%d").daemon(true).build());

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
        AuthenticationHelper authHelper = new AuthenticationHelper(activity);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect, container, false);

        ImageButton buttonBack = (ImageButton) view.findViewById(R.id.button_back_connect);
        ImageButton buttonStart = view.findViewById(R.id.button_start_connect);
        imageViewWaiting = view.findViewById(R.id.image_waiting);

        buttonBack.setBackgroundColor(Color.TRANSPARENT);
        buttonBack.setOnClickListener(v -> {
            if (activity != null){
                activity.replaceFragmentChoice();
            }
        });

        buttonStart.setOnClickListener(v -> {
            Glide.with(activity).load(R.drawable.icon_load).into(imageViewWaiting);

            if(!authHelper.isLogin()) // user didn't login
            {
                activity.startAuthenticationActivity();
            }
            else
            {
                Runnable requestTask = () -> {
                    String url = HttpHelper.IP + "/match?" + "user=" + authHelper.getUsername();
                    HttpHelper.sendGetRequest(url, this);
                };
                executorService.scheduleWithFixedDelay(requestTask, 0, 200, TimeUnit.MILLISECONDS);
            }
        });

        return view;
    }

    /**
     * OkHttp callback
     */
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        executorService.shutdown();
        if(activity != null){
            activity.runOnUiThread(()->{
                Toast.makeText(activity, "Failed in connection. Please try again.", Toast.LENGTH_SHORT).show();
            });
        }
    }

    /**
     * OkHttp callback
     */
    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//        Log.d("ConnectFragment", "on response start");

        String responseJson = response.body().string();
        ServerResponseData responseData = new ServerResponseData(responseJson);

        if(responseData.isMatched())
        {
            executorService.shutdown();
            Log.d( "ConnectFragment", "roomId: " + responseData.getRoomId());

            if(activity != null){
                activity.startGameActivity(Game.ONLINE);
            }
        }

//        Log.d("ConnectFragment", "on response down");
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
//            Log.d( "ConnectFragment", "roomId: " + roomId + " match_state: " + match_state);
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
                Log.d( "ConnectFragment", e.toString()) ;
                e.printStackTrace();
            }
        }
    }
}