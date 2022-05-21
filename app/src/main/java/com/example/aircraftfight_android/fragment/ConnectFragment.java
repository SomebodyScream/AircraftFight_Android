package com.example.aircraftfight_android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.MainActivity;


public class ConnectFragment extends Fragment {

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
        MainActivity activity = (MainActivity) getActivity();

        ImageButton buttonBack = (ImageButton) view.findViewById(R.id.button_back_connect);
        buttonBack.setBackgroundColor(Color.TRANSPARENT);

        buttonBack.setOnClickListener(v -> {
            if (activity != null){
                activity.replaceFragmentChoice();
            }
        });

        return view;
    }
}