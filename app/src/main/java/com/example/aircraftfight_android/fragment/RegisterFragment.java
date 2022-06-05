package com.example.aircraftfight_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.AuthenticationActivity;

public class RegisterFragment extends Fragment {


    public RegisterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        AuthenticationActivity activity = (AuthenticationActivity) getActivity();

        Button buttonGoLogin = view.findViewById(R.id.button_go_login);
        Button buttonRegisterConfirm = view.findViewById(R.id.button_register_confirm);

        buttonRegisterConfirm.setOnClickListener(v -> {
            //TODO
        });

        buttonGoLogin.setOnClickListener(v -> {
            if (activity!= null){
                activity.replaceFragmentLogin();
            }
        });
        return view;

    }

}