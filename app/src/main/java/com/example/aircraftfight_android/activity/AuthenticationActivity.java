package com.example.aircraftfight_android.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.fragment.AccountFragment;
import com.example.aircraftfight_android.fragment.LoginFragment;
import com.example.aircraftfight_android.fragment.RegisterFragment;
import com.example.aircraftfight_android.helper.SharedPreferenceHelper;

public class AuthenticationActivity extends BaseActivity {

    public static String SP_DATABASE_ACCOUNT = "accountConf";
    public static String SP_LABEL_USERNAME = "user_name";
    public static String SP_LABEL_LOGIN_STATUS = "login_status";
    public static String SP_LABEL_USER_TOKEN = "user_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        SharedPreferenceHelper sp = new SharedPreferenceHelper(this,SP_DATABASE_ACCOUNT);
        Boolean loginStatus = (Boolean) sp.readProperty(SP_LABEL_LOGIN_STATUS
                ,SharedPreferenceHelper.READ_MODE_BOOLEAN);
        Fragment fragment;
        if (loginStatus){
            fragment = new AccountFragment();
        }else{
            fragment = new LoginFragment();
        }

        replaceFragment(fragment);

        ImageButton buttonBack = findViewById(R.id.button_back_authentication);
        buttonBack.setBackgroundColor(Color.TRANSPARENT);

        buttonBack.setOnClickListener(v -> finish());
    }

    public void replaceFragmentLogin(){
        replaceFragment(new LoginFragment());
    }

    public void replaceFragmentRegister(){
        replaceFragment(new RegisterFragment());
    }

    public void replaceFragmentAccount(){
        replaceFragment(new AccountFragment());
    }

    public void failLogin(){
        Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show();
    }

    public void failRegister(){
        Toast.makeText(this, "Register fail", Toast.LENGTH_SHORT).show();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.authentication_activity_fragment_content,fragment);
        transaction.commit();
    }

}