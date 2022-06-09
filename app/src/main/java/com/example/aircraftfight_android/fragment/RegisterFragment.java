package com.example.aircraftfight_android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.activity.AuthenticationActivity;
import com.example.aircraftfight_android.helper.HttpHelper;
import com.example.aircraftfight_android.helper.SharedPreferenceHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class RegisterFragment extends Fragment {

    private final String ip = "http://43.154.151.8:8080/aut/regis?";
    private String username = "";
    private String passwd = "";
    private String passwdRe = "";

    private static class ResponseJson {
        private String stat;
        private String token;

        protected ResponseJson(String stat,String token){
            this.stat = stat;
            this.token = token;
        }
    }

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

        EditText userNameEdit = view.findViewById(R.id.user_name_register);
        EditText passWordEdit = view.findViewById(R.id.pass_wd_register);
        EditText passWordRe = view.findViewById(R.id.pass_wd_repeat);

        buttonRegisterConfirm.setOnClickListener(v -> {
            //TODO : Enhance username & passwd security
            username = userNameEdit.getText().toString();
            passwd = passWordEdit.getText().toString();
            passwdRe = passWordRe.getText().toString();
            if (passwd.equals(passwdRe)){
                String address = ip+"user="+username+"&passwd="+passwd;
                HttpHelper.sendGetRequest(address,new okhttp3.Callback() {

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Gson gson = new Gson();
                        String str = Objects.requireNonNull(response.body()).string();
                        ResponseJson rsp = gson.fromJson(str, ResponseJson.class);
                        if (rsp.stat.equals("ac")){
                            SharedPreferenceHelper sp = new SharedPreferenceHelper(
                                    activity,AuthenticationActivity.SP_DATABASE_ACCOUNT);
                            sp.writeProperty(AuthenticationActivity.SP_LABEL_LOGIN_STATUS,true);
                            sp.writeProperty(AuthenticationActivity.SP_LABEL_USERNAME,username);
                            sp.writeProperty(AuthenticationActivity.SP_LABEL_USER_TOKEN,rsp.token);
                            if (activity != null){
                                activity.replaceFragmentAccount();
                            }
                        }else{
                            if (activity!=null){
                                activity.runOnUiThread(activity::failRegister);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        //TODO
                    }
                });
            }else{
                Toast.makeText(activity, "Repeat password not match!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonGoLogin.setOnClickListener(v -> {
            if (activity!= null){
                activity.replaceFragmentLogin();
            }
        });
        return view;

    }

}