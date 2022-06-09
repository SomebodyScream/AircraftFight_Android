package com.example.aircraftfight_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.AuthenticationActivity;
import com.example.aircraftfight_android.helper.SharedPreferenceHelper;

public class AccountFragment extends Fragment {


    public AccountFragment() {
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        AuthenticationActivity activity = (AuthenticationActivity) getActivity();

        SharedPreferenceHelper sp = new SharedPreferenceHelper(activity,
                AuthenticationActivity.SP_DATABASE_ACCOUNT);

        TextView textView = view.findViewById(R.id.text_user_name);
        Button button = view.findViewById(R.id.button_logout);

        textView.setText((String)sp.readProperty(AuthenticationActivity.SP_LABEL_USERNAME,
                SharedPreferenceHelper.READ_MODE_STRING));

        button.setOnClickListener(v -> {
            sp.writeProperty(AuthenticationActivity.SP_LABEL_USERNAME,"false");
            sp.writeProperty(AuthenticationActivity.SP_LABEL_USER_TOKEN,"false");
            sp.writeProperty(AuthenticationActivity.SP_LABEL_LOGIN_STATUS,false);
            if (activity != null){
                activity.replaceFragmentLogin();
            }
        });

        return view;
    }

}