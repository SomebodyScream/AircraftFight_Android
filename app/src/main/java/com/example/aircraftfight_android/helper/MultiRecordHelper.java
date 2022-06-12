package com.example.aircraftfight_android.helper;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aircraftfight_android.activity.AuthenticationActivity;
import com.example.aircraftfight_android.fragment.LoginFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.OkHttp;
import okhttp3.Response;

public class MultiRecordHelper
{
    public static final int ORDER_SCORE_ASC = 0;
    public static final int ORDER_SCORE_DESC = 1;
    public static final int ORDER_DATE_ASC = 2;
    public static final int ORDER_DATE_DESC = 3;

    private Context context;
    private SharedPreferenceHelper spHelper;
    private String label = "ONLINE";

    private okhttp3.Callback callback;

    public List<MultiRecord> records;

    /**
     * Constructor will automatically read corresponding records from local
     */
    public MultiRecordHelper(Context context, okhttp3.Callback callback){
        this.callback = callback;
        this.context = context;
        spHelper = new SharedPreferenceHelper(context, "Records");


        AuthenticationHelper ap = new AuthenticationHelper(context);
        if (ap.isLogin()){
            pullRecords(HttpHelper.IP+"/rec?user="+ap.getUsername());
        }else{
            records = readRecordsFromLocal();
        }
    }

    /**
     * Add a new record (using current date)
     * The new record will be automatically saved to local
     */
    public void addRecord(String userName, int userScore, String opponentName, int opponentScore)
    {
        records.add(new MultiRecord(userName, opponentName, userScore, opponentScore, new Date()));
        saveRecordsToLocal();
    }

    /**
     * Add a new record (using specified date)
     * The new record will be automatically saved to local
     */
    public void addRecord(String userName, int userScore, String opponentName, int opponentScore, Date date)
    {
        records.add(new MultiRecord(userName, opponentName, userScore, opponentScore, date));
        saveRecordsToLocal();
    }

    /**
     * Remove specified record r
     * The records list will be automatically saved to local after removing
     */
    public void removeRecord(MultiRecord r)
    {
        for(int i=0; i<records.size(); i++) {
            if(records.get(i).equals(r)) {
                records.remove(i);
                break;
            }
        }
        saveRecordsToLocal();
    }

    /**
     * Remove specified index of record
     * The records list will be automatically saved to local after removing
     */
    public void removeRecord(int idx)
    {
        if(idx < 0 || idx >= records.size()) return;
        records.remove(idx);
        saveRecordsToLocal();
    }

    /**
     * Return record with specified index (null when index is invalid)
     */
    public MultiRecord getRecord(int idx){
        if(idx < 0 || idx >= records.size()) return null;
        return records.get(idx);
    }

    /**
     * Return all records
     */
    public List<MultiRecord> getAllRecords(){
        if(records == null){
            records = readRecordsFromLocal();
        }
        return records;
    }

    /**
     * Return all records in specified order (see static field defined in this class)
     *
     * Warning: This operation will change the order of the records list hold by helper.
     * Please be careful when using "remove(int idx)" after using this method.
     */
    public List<MultiRecord> getAllRecords(int order){
        if(records == null){
            records = readRecordsFromLocal();
        }

        if(order == ORDER_SCORE_DESC){
            records.sort((MultiRecord r1, MultiRecord r2) -> r2.getUserScore() - r1.getUserScore());
        }
        else if(order == ORDER_SCORE_ASC){
            records.sort(Comparator.comparingInt(MultiRecord::getUserScore));
        }
        else if(order == ORDER_DATE_DESC){
            records.sort((MultiRecord r1, MultiRecord r2) -> r1.getDate().after(r2.getDate()) ?-1 :1);
        }
        else if(order == ORDER_DATE_ASC){
            records.sort((MultiRecord r1, MultiRecord r2) -> r1.getDate().before(r2.getDate()) ?-1 :1);
        }
        return records;
    }

    /**
     * Read records from local storage
     * @return List<MultiRecord>
     */
    public List<MultiRecord> readRecordsFromLocal()
    {
        String jsonList = (String) spHelper.readProperty(label, SharedPreferenceHelper.READ_MODE_STRING);

        // deserialize json to get original list
        if(!jsonList.equals(SharedPreferenceHelper.DEFAULT_VALUE)){
            Gson gson = new Gson();
            return gson.fromJson(jsonList, new TypeToken<LinkedList<MultiRecord>>(){}.getType());
        }
        else {
            return new LinkedList<>();
        }
    }

    /**
     * Save records to local storage
     */
    public void saveRecordsToLocal()
    {
        Gson gson = new Gson();
        String jsonList = gson.toJson(records);

        spHelper.writeProperty(label, jsonList);
    }

    private void pullRecords(String address){
        HttpHelper.sendGetRequest(address,callback);
    }
}
