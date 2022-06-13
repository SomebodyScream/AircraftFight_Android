package com.example.aircraftfight_android.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.adapter.SingleRecordAdapter;
import com.example.aircraftfight_android.callback.RecordActivityCallBack;
import com.example.aircraftfight_android.game.application.Game;
import com.example.aircraftfight_android.helper.AuthenticationHelper;
import com.example.aircraftfight_android.helper.SingleRecord;
import com.example.aircraftfight_android.helper.SingleRecordHelper;

public class SingleRecordFragment extends Fragment {

    private Context context;
    private SingleRecordHelper recordHelper;
    private LinearLayout mRecordDetail;

    private String gameMode="";

    SingleRecordAdapter singleAdapter;

    private int curSelectedPosition = -1;

    public SingleRecordFragment(String gameMode) {
        if (gameMode == null){
            this.gameMode = Game.EASY;
        }else{
            this.gameMode = gameMode;
        }
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
        View view = inflater.inflate(R.layout.fragment_single_record, container, false);
        context = getActivity();

        // Initial record management
        recordHelper = new SingleRecordHelper(context, gameMode);

        // Initial record detail pop window
        initRecordDetailPage(view);

        // Initial recycler view
        initRecyclerView(view);

        return view;
    }

    /**
     * Initial record detail pop window
     */
    private void initRecordDetailPage(View view)
    {
        mRecordDetail = (LinearLayout) view.findViewById(R.id.draw_layout_record);
        mRecordDetail.setVisibility(View.GONE);

        Button backButton = view.findViewById(R.id.button_draw_back);
        Button deleteButton = view.findViewById(R.id.button_draw_delete);

        backButton.setOnClickListener(v -> {
            curSelectedPosition = -1;
            hideRecordDetail();
        });

        deleteButton.setOnClickListener(v -> {
            if(curSelectedPosition != -1){
                recordHelper.removeRecord(curSelectedPosition);
                curSelectedPosition = -1;
                hideRecordDetail();
                refreshRecyclerView(view);//Need to refresh list here!
            }
        });
    }

    private void initRecyclerView(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_record);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        TextView playerNameView = view.findViewById(R.id.text_view_record_name);
        TextView scoreView = view.findViewById(R.id.text_view_record_score);
        TextView dateView = view.findViewById(R.id.text_view_record_date);

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
        refreshRecyclerView(view);
    }

    private void refreshRecyclerView(View view)
    {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_record);
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