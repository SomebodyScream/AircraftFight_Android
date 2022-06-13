package com.example.aircraftfight_android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.callback.RecordActivityCallBack;
import com.example.aircraftfight_android.helper.SingleRecord;

import java.util.List;

public class SingleRecordAdapter extends RecyclerView.Adapter<SingleRecordAdapter.ViewHolder>
{
    private final List<SingleRecord> singleRecords;
    private final RecordActivityCallBack callBack;

    public SingleRecordAdapter(List<SingleRecord> singleRecords, RecordActivityCallBack callback){
        this.singleRecords = singleRecords;
        this.callBack = callback;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        TextView scoreView;
        TextView dateView;
        View parentView;

        public ViewHolder(View view){
            super(view);
            parentView = view;
            nameView = view.findViewById(R.id.name);
            scoreView = view.findViewById(R.id.score);
            dateView = view.findViewById(R.id.date);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_record, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.parentView.setOnClickListener(v -> {
            int position = holder.getBindingAdapterPosition();
            callBack.recordOnClick(position);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SingleRecord record = singleRecords.get(position);
        holder.nameView.setText(record.getPlayerName());
        holder.scoreView.setText(String.valueOf(record.getScore()));
        holder.dateView.setText(record.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return singleRecords.size();
    }
}
