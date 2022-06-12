package com.example.aircraftfight_android.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.RecordActivity;
import com.example.aircraftfight_android.adapter.MultiRecordAdapter;
import com.example.aircraftfight_android.game.application.Game;
import com.example.aircraftfight_android.helper.MultiRecord;
import com.example.aircraftfight_android.helper.MultiRecordHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
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

        TextView difficultyView = view.findViewById(R.id.difficulty_view);
        difficultyView.setText("Mode: " + Game.ONLINE);

        // Initial record management and add a new column
        recordHelper = new MultiRecordHelper(activity,this);

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

    private void refreshRecyclerView()
    {
        multiRecordAdapter = new MultiRecordAdapter(
                recordHelper.getAllRecords(MultiRecordHelper.ORDER_SCORE_DESC));
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_record);
        recyclerView.setAdapter(multiRecordAdapter);
        Log.d("TAG", "onResponse: 1");
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        Gson gson = new Gson();
        String str = Objects.requireNonNull(response.body()).string();
        try {
            JSONArray jsonArray = new JSONArray(str);
            for (int i = 0; i< jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MultiRecord multiRecord= new MultiRecord(jsonObject.getString("mn"),
                        jsonObject.getString("gn"),jsonObject.getInt("ms"),
                        jsonObject.getInt("gs"),new Date());
                recordHelper.records.add(multiRecord);
                Log.d("TAG", "onResponse: added");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        activity.runOnUiThread(this::refreshRecyclerView);
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        //TODO
    }

}