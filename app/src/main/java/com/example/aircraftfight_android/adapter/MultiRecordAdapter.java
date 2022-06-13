package com.example.aircraftfight_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.callback.RecordActivityCallBack;
import com.example.aircraftfight_android.helper.MultiRecord;
import com.example.aircraftfight_android.helper.SingleRecord;

import java.util.LinkedList;
import java.util.List;

public class MultiRecordAdapter extends RecyclerView.Adapter<MultiRecordAdapter.ViewHolder>
{
    private List<MultiRecord> multiRecords= new LinkedList<>();
    private Activity activity;

    public MultiRecordAdapter(Activity activity,List<MultiRecord> multiRecords){
        this.activity = activity;
        this.multiRecords = multiRecords;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameMaster;
        TextView nameGuest;
        TextView scoreMaster;
        TextView scoreGuest;
        TextView dateView;
        View parentView;

        public ViewHolder(View view){
            super(view);
            parentView = view;
            nameMaster = view.findViewById(R.id.master_name);
            nameGuest = view.findViewById(R.id.guest_name);
            scoreMaster = view.findViewById(R.id.master_score);
            scoreGuest = view.findViewById(R.id.guest_score);
            dateView = view.findViewById(R.id.multi_time);
            nameMaster.setTypeface(Typeface.createFromAsset(activity.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
            nameGuest.setTypeface(Typeface.createFromAsset(activity.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
            scoreMaster.setTypeface(Typeface.createFromAsset(activity.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
            scoreGuest.setTypeface(Typeface.createFromAsset(activity.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
            dateView.setTypeface(Typeface.createFromAsset(activity.getAssets(), "AveriaSerifLibre-Italic-4.ttf"));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multi_record, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MultiRecord record = multiRecords.get(position);
        holder.nameMaster.setText(record.getUserName());
        holder.scoreMaster.setText(String.valueOf(record.getUserScore()));
        holder.nameGuest.setText(record.getOpponentName());
        holder.scoreGuest.setText(String.valueOf(record.getOpponentScore()));
        holder.dateView.setText(record.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return multiRecords.size();
    }
}
