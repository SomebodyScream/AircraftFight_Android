package com.example.aircraftfight_android.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.aircraftfight_android.service.MusicService;

public class MusicServiceHelper {

    Context context;
    private MusicService.MusicBinder binder;

    public MusicServiceHelper(Context context){
        this.context = context;
        Intent intent = new Intent(context, MusicService.class);
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (MusicService.MusicBinder) service;
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        context.bindService(intent,connection,Context.BIND_AUTO_CREATE);
    }

    public void startBackgroundMusic(){
        binder.startBackgroundMusic();
    }

    public void pauseBackgroundMusic(){
        binder.pauseBackgroundMusic();
    }

    public void startBossMusic(){
        binder.startBossMusic();
    }

    public void stopBossMusic(){
        binder.stopBossMusic();
    }

    public void playSoundEffect(int fileId){
        binder.playSoundEffect(fileId);
    }
}
