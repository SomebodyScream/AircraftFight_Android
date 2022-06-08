package com.example.aircraftfight_android.helper;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.aircraftfight_android.activity.AuthenticationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class AuthenticationHelper {

    private final String ip = "http://43.154.151.8:8080/aut/login?";
    private String username = "";
    private String token = "";
    private Boolean status = false;
    private SharedPreferenceHelper sp;
    private JSONObject jsonObject;

    private Context context;

    /**
     * Constructor method
     * @param context Activity needed
     */
    public AuthenticationHelper(Context context){
        this.context = context;
        sp = new SharedPreferenceHelper(context,
                AuthenticationActivity.SP_DATABASE_ACCOUNT);
        init();
    }

    public String getUsername() {
        init();
        return username;
    }

    /**
     * Use to verify the login status, data based on local storage, not the newest.
     * @return a boolean value indicates the login status
     */
    public boolean isLogin(){
        init();
        return status;
    }

    /**
     * Use to check the login status, this method will communicate with the server to check whether
     * current user status is reliable
     * @return a boolean value indicates the login status
     */
    public boolean checkLogin(){
        String address = ip+"aut_mode=token&user="+username+"&token="+token;
        HttpHelper.sendOkHttpRequest(address,new okhttp3.Callback(){

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    jsonObject = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (jsonObject.getString("stat").equals("ac")){
                        sp.writeProperty(AuthenticationActivity.SPLABEL_LOGIN_STATUS,true);
                        sp.writeProperty(AuthenticationActivity.SPLABEL_USERNAME,username);
                        sp.writeProperty(AuthenticationActivity.SPLABEL_USER_TOKEN,jsonObject.getString("token"));
                    }else{
                        sp.writeProperty(AuthenticationActivity.SPLABEL_LOGIN_STATUS,false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
        });
        init();
        return status;
    }

    private void init(){
        username = (String) sp.readProperty(AuthenticationActivity.SPLABEL_USERNAME,
                SharedPreferenceHelper.READ_MODE_STRING);
        token = (String) sp.readProperty(AuthenticationActivity.SPLABEL_USER_TOKEN,
                SharedPreferenceHelper.READ_MODE_STRING);
        status = (Boolean) sp.readProperty(AuthenticationActivity.SPLABEL_LOGIN_STATUS,
                SharedPreferenceHelper.READ_MODE_BOOLEAN);
    }
}
