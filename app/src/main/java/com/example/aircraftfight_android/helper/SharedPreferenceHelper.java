package com.example.aircraftfight_android.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {

    private SharedPreferences.Editor editor;
    private final Context context;
    private final String dataBaseLabel;

    public static int READ_MODE_STRING = 0;
    public static int READ_MODE_BOOLEAN = 1;
    public static int READ_MODE_INT = 2;
    public static int READ_MODE_FLOAT = 3;

    public SharedPreferenceHelper(Context context,String label){
        this.context = context;
        this.dataBaseLabel = label;
    }

    /**
     * write property method : to write a string-string content
     * into the shared preference storage
     * @param label the label of the content (as index)
     * @param content the content
     */
    public void writeProperty(String label, String content){
        editor = context.getSharedPreferences(dataBaseLabel,Context.MODE_PRIVATE).edit();
        editor.putString(label,content);
        editor.apply();
    }

    /**
     * write property method : to write a string-boolean content
     * into the shared preference storage
     * @param label the label of the content (as index)
     * @param content the content
     */
    public void writeProperty(String label, Boolean content){
        editor = context.getSharedPreferences(dataBaseLabel,Context.MODE_PRIVATE).edit();
        editor.putBoolean(label,content);
        editor.apply();
    }

    /**
     * write property method : to write a string-int content
     * into the shared preference storage
     * @param label the label of the content (as index)
     * @param content the content
     */
    public void writeProperty(String label, int content){
        editor = context.getSharedPreferences(dataBaseLabel,Context.MODE_PRIVATE).edit();
        editor.putInt(label,content);
        editor.apply();
    }

    /**
     * write property method : to write a string-float content
     * into the shared preference storage
     * @param label the label of the content (as index)
     * @param content the content
     */
    public void writeProperty(String label, float content){
        editor = context.getSharedPreferences(dataBaseLabel,Context.MODE_PRIVATE).edit();
        editor.putFloat(label,content);
        editor.apply();
    }

    /**
     * read property method : to read a string-string content
     * from the shared preference storage
     * @param label the label of the content (as index)
     * @param mode read mode
     * @return read result
     * COULD RETURN NULL!
     */
    public Object readProperty(String label,int mode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(dataBaseLabel, Context.MODE_PRIVATE);
        switch (mode){
            case 0:{
                return sharedPreferences.getString(label,"Default value");
            }
            case 1:{
                return sharedPreferences.getBoolean(label,false);
            }
            case 2:{
                return sharedPreferences.getInt(label,-1);
            }
            case 3:{
                return sharedPreferences.getFloat(label,-1);
            }
        }
        return null;
    }

    /**
     * Clear an sp database,this will clear everything in the standalone file
     * @param clearDatabase the name of sp database to be cleared
     */
    public void clearProperty(String clearDatabase){
        editor = context.getSharedPreferences(clearDatabase,Context.MODE_PRIVATE).edit();
        editor.clear().apply();
    }

}
