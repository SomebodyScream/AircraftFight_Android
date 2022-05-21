package com.example.aircraftfight_android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Window;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.fragment.ChoiceFragment;
import com.example.aircraftfight_android.fragment.SingleRecordFragment;

public class RecordActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_record);
        Fragment fragment = new SingleRecordFragment();
        initialFragment(fragment);
    }

    private void initialFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.record_activity_fragment_content,fragment);
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.record_activity_fragment_content,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}