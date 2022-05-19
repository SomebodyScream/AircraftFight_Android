package com.example.aircraftfight_android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {

    private MusicBinder binder = new MusicBinder();

    public class MusicBinder extends Binder {
        public void startBackgroundMusic(){
            Log.d("Service", "execute");
        }
        public void pauseBackgroundMusic(){
        }

        public void startBossMusic(){
        }

        public void stopBossMusic(){
        }

        public void playSoundEffect(int fileId){
        }
    }

    public MusicService() {
    }

    @Override
    public void onCreate(){
        Log.d("Service", "Music service on create");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags , int startId){
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        Log.d("Service", "Music service on destroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}