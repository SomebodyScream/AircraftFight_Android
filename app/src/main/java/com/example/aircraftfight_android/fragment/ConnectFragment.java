package com.example.aircraftfight_android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.application.Game;
import com.example.aircraftfight_android.helper.HttpHelper;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.RequestBody;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connect, container, false);
        activity = (MainActivity) getActivity();

        ImageButton buttonBack = (ImageButton) view.findViewById(R.id.button_back_connect);
        ImageButton buttonStart = view.findViewById(R.id.button_start_connect);
        imageViewWaiting = view.findViewById(R.id.image_waiting);

        /**
         *  DEBUG CODE
         *  should be deleted later
         */
        EditText editText = view.findViewById(R.id.editText_test_connect);

        buttonBack.setBackgroundColor(Color.TRANSPARENT);
        buttonBack.setOnClickListener(v -> {
            if (activity != null){
                activity.replaceFragmentChoice();
            }
        });

        /**
         * Do NOT delete !!!
         */
//        Runnable requestTask = () -> {
//            HttpHelper.sendGetRequest(HttpHelper.IP + "/match?" + "user=" + user, this);
//        };

        buttonStart.setOnClickListener(v -> {
            Glide.with(activity).load(R.drawable.icon_load).into(imageViewWaiting);

            /**
             *  DEBUG CODE
             *  should be deleted later
             */
            String user = editText.getText().toString();

//            HttpHelper.sendGetRequest(HttpHelper.IP + "/match?" + "user=" + user, this);

            RequestTask requestTask = new RequestTask(user, this);
            executorService.scheduleWithFixedDelay(requestTask, 0, 200, TimeUnit.MILLISECONDS);
        });

        return view;
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        if(activity != null){
            activity.runOnUiThread(()->{
                Toast.makeText(activity, "Failed in connection. Please try again.", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        Log.d("ConnectFragment", "on response");

        String responseJson = response.body().string();
        String roomId = parseResponseJson(responseJson);

        if(roomId != null)
        {
            executorService.shutdown();

//            if(activity != null){
//                activity.startGameActivity(Game.EASY);
//            }
        }

        Log.d("ConnectFragment", "response down");
    }

    /**
     * 解析服务器回传的对手匹配状态
     * @param jsonData 服务器回传的json，形式为 {roomId: xxx, match_state: true/false}
     * @return 若match_state==true,表示匹配成功，返回String型roomId，否则返回null
     */
    private String parseResponseJson(String jsonData)
    {
        try{
            JSONObject jsonObject = new JSONObject(jsonData);

            String roomId = jsonObject.getString( "roomId" );
            boolean match_state = jsonObject.getBoolean( "match_state");

            Log.d( "ConnectFragment", "roomId: " + roomId + " match_state: " + match_state);

            return match_state ?roomId :null;
        }
        catch(Exception e){
            Log.d( "ConnectFragment", e.toString()) ;
            e.printStackTrace();
            return null;
        }
    }
}

/**
 * DEBUG CODE
 * can be replaced with Lambda later
 */
class RequestTask implements Runnable
{
    private String user;
    private okhttp3.Callback callback;

    public RequestTask(String user, okhttp3.Callback callback){
        this.user = user;
        this.callback = callback;
    }

    public void run(){
        HttpHelper.sendGetRequest(HttpHelper.IP + "/match?" + "user=" + user, callback);
    }
}