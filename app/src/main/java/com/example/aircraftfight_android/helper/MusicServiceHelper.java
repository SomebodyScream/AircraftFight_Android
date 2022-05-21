package com.example.aircraftfight_android.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.service.MusicService;

public class MusicServiceHelper {

    private Context context;
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

    public void bindService(Context context){
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
        binder.stopBackgroundMusic();
    }

    public void startBossMusic(){
        while (binder==null){
            Log.d("Service", "startBossMusic: NULL detected");
        }
        binder.startBossMusic();
    }

    public void stopBossMusic(){
        binder.stopBossMusic();
    }

    public void playHeroBulletShoot(){
        binder.playSoundEffect(1);
    }

    public void playHeroBomb(){
        binder.playSoundEffect(2);
    }

    public void playEnemyShoot(){
        binder.playSoundEffect(3);
    }

    public void playEnemyBomb(){
        binder.playSoundEffect(4);
    }

    public void playEnemyCrash(){
        binder.playSoundEffect(5);
    }

    public void playGameOver(){
        binder.playSoundEffect(6);
    }
}
