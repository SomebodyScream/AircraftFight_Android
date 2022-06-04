package com.example.aircraftfight_android.helper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpHelper {
    public static final String IP = "http://localhost:8080/AircraftFight_Server_war_exploded";
//    public static final String IP = "http://43.154.151.8:8080";
//    public static final String PORT = "8080";

    @Deprecated
    public static void sendOkHttpRequest(final String address, final okhttp3.Callback callback) {
        sendGetRequest(address, callback);
    }

    public static void sendGetRequest(final String address, final okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendPostRequest(final String address, final RequestBody requestBody, final okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
