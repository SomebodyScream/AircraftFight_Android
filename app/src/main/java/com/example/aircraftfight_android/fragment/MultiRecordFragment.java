package com.example.aircraftfight_android.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.RecordActivity;
import com.example.aircraftfight_android.adapter.MultiRecordAdapter;
import com.example.aircraftfight_android.helper.MultiRecord;
import com.example.aircraftfight_android.helper.MultiRecordHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class MultiRecordFragment extends Fragment implements okhttp3.Callback{

    private RecordActivity activity;
    private MultiRecordHelper recordHelper;
    MultiRecordAdapter multiRecordAdapter;

    private View view;

    public MultiRecordFragment() {
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
        view = inflater.inflate(R.layout.fragment_multi_record, container, false);
        activity = (RecordActivity) getActivity();

        // Initial record management and add a new column
        recordHelper = new MultiRecordHelper(activity,this);

        ImageButton refreshButton = view.findViewById(R.id.refresh_list);
        refreshButton.setBackgroundColor(Color.TRANSPARENT);

        refreshButton.setOnClickListener(v -> {
            Toast.makeText(activity, "Pulling from server", Toast.LENGTH_SHORT).show();
            recordHelper = new MultiRecordHelper(activity,this);
            initRecyclerView();
        });

        // Initial recycler view
        initRecyclerView();

        return view;
    }

    private void initRecyclerView()
    {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_record);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        refreshRecyclerView();
    }

    @SuppressLint("SetTextI18n")
    private void refreshUI(MultiRecord multiRecord)
    {
        if (multiRecord!=null){
            TextView textWinner = view.findViewById(R.id.text_winner);
            TextView textGuestScore = view.findViewById(R.id.text_guest_score);
            TextView textMasterScore = view.findViewById(R.id.text_master_score);
            TextView textTime = view.findViewById(R.id.text_time);
            if (multiRecord.getUserScore()>=multiRecord.getOpponentScore()){
                textWinner.setText("Winner : "+multiRecord.getUserName());
            }else{
                textWinner.setText("Winner : "+multiRecord.getOpponentName());
            }
            textMasterScore.setText("Master score : "+ multiRecord.getUserScore());
            textGuestScore.setText("Opponent score : "+ multiRecord.getOpponentScore());
            textTime.setText("Time : "+multiRecord.getDate().toString());
            textWinner.setSelected(true);
            textGuestScore.setSelected(true);
            textMasterScore.setSelected(true);
            textTime.setSelected(true);
            textWinner.setTypeface(Typeface.createFromAsset(activity.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
            textGuestScore.setTypeface(Typeface.createFromAsset(activity.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
            textMasterScore.setTypeface(Typeface.createFromAsset(activity.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
            textTime.setTypeface(Typeface.createFromAsset(activity.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
        }
    }

    private void refreshRecyclerView(){
        multiRecordAdapter = new MultiRecordAdapter(activity,
                recordHelper.getAllRecords(MultiRecordHelper.ORDER_SCORE_DESC));
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_record);
        recyclerView.setAdapter(multiRecordAdapter);
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        Gson gson = new Gson();
        String str = Objects.requireNonNull(response.body()).string();
        try {
            JSONArray jsonArray = new JSONArray(str);
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");
            for (int i = 0; i< jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MultiRecord multiRecord= new MultiRecord(jsonObject.getString("mn"),
                        jsonObject.getString("gn"),jsonObject.getInt("ms"),
                        jsonObject.getInt("gs"),format.parse(jsonObject.getString("tm")));
                recordHelper.records.add(multiRecord);
                Log.d("TAG", "onResponse: added");
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        recordHelper.addRecords();
        if (!recordHelper.getAllRecords(MultiRecordHelper.ORDER_DATE_DESC).isEmpty()){
            MultiRecord mostRecentRecord = recordHelper.getAllRecords(MultiRecordHelper.ORDER_DATE_DESC).get(0);
            activity.runOnUiThread(() -> {
                refreshUI(mostRecentRecord);
                refreshRecyclerView();
            });
        }else{
            activity.runOnUiThread(this::refreshRecyclerView);
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        //TODO
    }

}