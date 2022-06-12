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
import com.example.aircraftfight_android.helper.AuthenticationHelper;
import com.example.aircraftfight_android.helper.SingleRecordHelper;
import com.example.aircraftfight_android.helper.SingleRecord;

public class RecordActivity extends BaseActivity {

    private SingleRecordHelper recordHelper;

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

        // 初始化标题显示游戏模式
        TextView difficultyView = findViewById(R.id.difficulty_view);
        difficultyView.setText("Mode: " + gameMode);

        // 获取玩家姓名
        String playerName = "Anonymous";
        AuthenticationHelper authHelper = new AuthenticationHelper(this);
        if(authHelper.isLogin()){
            playerName = authHelper.getUsername();
        }

        // 初始化记录管理器并添加新记录
        recordHelper = new SingleRecordHelper(this, gameMode);
        recordHelper.addRecord(playerName, score);

        // 初始化详细记录弹窗
        initRecordDetailPage();

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
                recordHelper.removeRecord(curSelectedPosition);
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
                SingleRecord record = recordHelper.getRecord(position);

                playerNameView.setText(record.getPlayerName());
                scoreView.setText(String.valueOf(record.getScore()));
                dateView.setText(record.getDate().toString());
                showRecordDetail();
            }
        };

        singleAdapter = new SingleRecordAdapter(
                recordHelper.getAllRecords(SingleRecordHelper.ORDER_SCORE_DESC), callBack);
        refreshRecyclerView();
    }

    private void refreshRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_record);
        recyclerView.setAdapter(singleAdapter);
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