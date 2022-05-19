package com.example.aircraftfight_android.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.aircraftfight_android.R;

import java.util.HashMap;

public class MusicService extends Service {

    private final MusicBinder binder = new MusicBinder();
    private MediaPlayer player;
    private SoundPool soundPool;
    private final HashMap<Integer, Integer> soundEffectHash = new HashMap<Integer, Integer>();

    private static final int SE_HERO_BULLET_SHOOT = R.raw.bullet;
    private static final int SE_HERO_BOMB = R.raw.bomb_explosion;
    private static final int SE_ENEMY_SHOOT = R.raw.bullet;
    private static final int SE_ENEMY_BOMB = R.raw.bomb_explosion;
    private static final int SE_ENEMY_CRASH = R.raw.bullet_hit;
    private static final int SE_GAME_OVER = R.raw.game_over;

    private static final int SOUND_POOL_MAX_STREAMS = 50;

    public class MusicBinder extends Binder {
        public void startBackgroundMusic(){
            playBGM(R.raw.bgm01_eastern_night);
        }
        public void stopBackgroundMusic(){
            stopBgm();
        }

        public void startBossMusic(){
            playBGM(R.raw.bgm02_night_bird);
        }

        public void stopBossMusic(){
            stopBgm();
        }

        public void playSoundEffect(int id) {
            soundPool.play(soundEffectHash.get(id), 1, 1, 0, 0, 1);
        }
    }

    public MusicService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("Service", "Music service on create");
        soundPool= new SoundPool.Builder()
                .setMaxStreams(SOUND_POOL_MAX_STREAMS)
                .build();
        soundEffectHash.put(1,soundPool.load(this,SE_HERO_BULLET_SHOOT,1));
        soundEffectHash.put(2,soundPool.load(this,SE_HERO_BOMB,1));
        soundEffectHash.put(3,soundPool.load(this,SE_ENEMY_SHOOT,1));
        soundEffectHash.put(4,soundPool.load(this,SE_ENEMY_BOMB,1));
        soundEffectHash.put(5,soundPool.load(this,SE_ENEMY_CRASH,1));
        soundEffectHash.put(6,soundPool.load(this,SE_GAME_OVER,1));
    }

    @Override
    public int onStartCommand(Intent intent, int flags , int startId){
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        Log.d("Service", "Music service on destroy");
        stopBgm();
        soundPool.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void playBGM(int bgmId){
        if(player == null){
            player = MediaPlayer.create(this,bgmId);
            player.setLooping(true);
        }
        player.start();
    }

    private void stopBgm(){
        if (player != null) {
            player.stop();
            player.reset();
            player.release();
            player = null;
        }
    }

}