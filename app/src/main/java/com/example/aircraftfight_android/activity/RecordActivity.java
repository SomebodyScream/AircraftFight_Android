package com.example.aircraftfight_android.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.adapter.SingleRecordAdapter;
import com.example.aircraftfight_android.callback.RecordActivityCallBack;
import com.example.aircraftfight_android.helper.MultiRecord;
import com.example.aircraftfight_android.helper.SharedPreferenceHelper;
import com.example.aircraftfight_android.helper.SingleRecord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.LinkedList;

public class RecordActivity extends BaseActivity {

    private LinkedList<SingleRecord> singleRecords= new LinkedList<>();
    private LinkedList<MultiRecord> multiRecords= new LinkedList<>();

    private SharedPreferenceHelper singleSpHelper;
    private SharedPreferenceHelper multiSpHelper;

    SingleRecordAdapter singleAdapter;

    private LinearLayout mRecordDetail;
    private int curSelectedPosition = -1;

    private String gameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_record);

        // 获取GameActivity传递的数据
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        gameMode = intent.getStringExtra("gameMode");

        TextView difficultyView = findViewById(R.id.difficulty_view);
        difficultyView.setText("Mode: " + gameMode);

        // 初始化详细记录弹窗
        initRecordDetailPage();

        // 从文件加载得分记录并添加本次记录
        initSingleRecords(score);

        // 初始化RecyclerView
        initRecyclerView();
    }

    private void initRecordDetailPage()
    {
        mRecordDetail = (LinearLayout) findViewById(R.id.draw_layout_record);
        mRecordDetail.setVisibility(View.GONE);
        //        hideRecordDetail();

        Button backButton = findViewById(R.id.button_draw_back);
        Button deleteButton = findViewById(R.id.button_draw_delete);
        ImageButton closeButton = findViewById(R.id.button_back_record);

        closeButton.setBackgroundColor(Color.TRANSPARENT);

        backButton.setOnClickListener(v -> {
            curSelectedPosition = -1;
            hideRecordDetail();
        });

        deleteButton.setOnClickListener(v -> {
            if(curSelectedPosition != -1){
                singleRecords.remove(curSelectedPosition);
                saveRecordsToLocal();
                curSelectedPosition = -1;
                hideRecordDetail();
                refreshRecyclerView();//Need to refresh list here!
            }
        });

        closeButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_record);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TextView playerNameView = findViewById(R.id.text_view_record_name);
        TextView scoreView = findViewById(R.id.text_view_record_score);
        TextView dateView = findViewById(R.id.text_view_record_date);

        RecordActivityCallBack callBack = new RecordActivityCallBack() {
            @Override
            public void recordOnClick(int position) {
                curSelectedPosition = position;
                SingleRecord record = singleRecords.get(position);
                playerNameView.setText(record.getPlayerName());
                scoreView.setText(String.valueOf(record.getScore()));
                dateView.setText(record.getDate().toString());
                showRecordDetail();
            }
        };

        singleAdapter = new SingleRecordAdapter(singleRecords, callBack);
        refreshRecyclerView();
    }

    private void refreshRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_record);
        recyclerView.setAdapter(singleAdapter);
    }

    private void initSingleRecords(int score)
    {
        // 获取SPhelper
        singleSpHelper = new SharedPreferenceHelper(this, "SingleRecords");
        String jsonList = (String) singleSpHelper.readProperty(gameMode, SharedPreferenceHelper.READ_MODE_STRING);

        // json反序列化还原List
        if(!jsonList.equals(SharedPreferenceHelper.DEFAULT_VALUE)){
            Gson gson = new Gson();
            singleRecords = gson.fromJson(jsonList, new TypeToken<LinkedList<SingleRecord>>(){}.getType());
        }

        addSingleRecord(score);
    }

    private void addSingleRecord(int score)
    {
        singleRecords.add(new SingleRecord("test", score, new Date()));
        singleRecords.sort((SingleRecord r1, SingleRecord r2) -> r2.getScore() - r1.getScore());
        saveRecordsToLocal();
    }

    private void saveRecordsToLocal()
    {
        Gson gson = new Gson();
        String jsonList = gson.toJson(singleRecords);

        singleSpHelper.writeProperty(gameMode, jsonList);
    }

    private void showRecordDetail(){
        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, -1, TranslateAnimation.RELATIVE_TO_SELF, 0);
        ctrlAnimation.setDuration(200);     //设置动画的过渡时间

        mRecordDetail.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecordDetail.startAnimation(ctrlAnimation);
                mRecordDetail.setVisibility(View.VISIBLE);
            }
        }, 100);
    }

    /**
     * Hide Favor $ Bookmark page with an animation effect
     * move from down to top
     * global view elements required
     */
    private void hideRecordDetail(){
        final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, -1);
        ctrlAnimation.setDuration(200);     //设置动画的过渡时间

        mRecordDetail.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecordDetail.startAnimation(ctrlAnimation);
                mRecordDetail.setVisibility(View.GONE);
            }
        }, 100);
    }
}