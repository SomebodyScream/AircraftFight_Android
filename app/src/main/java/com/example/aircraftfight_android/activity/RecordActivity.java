package com.example.aircraftfight_android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.adapter.SingleRecordAdapter;
import com.example.aircraftfight_android.helper.MultiRecord;
import com.example.aircraftfight_android.helper.SharedPreferenceHelper;
import com.example.aircraftfight_android.helper.SingleRecord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {

    private LinkedList<SingleRecord> singleRecords= new LinkedList<>();
    private LinkedList<MultiRecord> multiRecords= new LinkedList<>();

    private SharedPreferenceHelper singleSpHelper;
    private SharedPreferenceHelper multiSpHelper;

    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_record);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        difficulty = intent.getStringExtra("difficulty");

        initSingleRecords(score);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_record);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SingleRecordAdapter adapter = new SingleRecordAdapter(singleRecords);
        recyclerView.setAdapter(adapter);
    }

    private void initSingleRecords(int score)
    {
        Log.d("RecordActivity", "getting sphelper");

        // 获取SPhelper
        singleSpHelper = new SharedPreferenceHelper(this, "SingleRecords");
        String jsonList = (String) singleSpHelper.readProperty(difficulty, SharedPreferenceHelper.READ_MODE_STRING);

        Log.d("RecordActivity", "deserialize " + jsonList);

        // json反序列化还原List
        if(!jsonList.equals(SharedPreferenceHelper.DEFAULT_VALUE)){
            Gson gson = new Gson();
            singleRecords = gson.fromJson(jsonList, new TypeToken<LinkedList<SingleRecord>>(){}.getType());
        }

        Log.d("RecordActivity", "add new record");

        addSingleRecord(score);
    }

    private void addSingleRecord(int score)
    {
        singleRecords.add(new SingleRecord("test", score, new Date()));
        saveRecordsToLocal();
    }

    private void saveRecordsToLocal()
    {
        Gson gson = new Gson();
        String jsonList = gson.toJson(singleRecords);

        singleSpHelper.writeProperty(difficulty, jsonList);
    }
}