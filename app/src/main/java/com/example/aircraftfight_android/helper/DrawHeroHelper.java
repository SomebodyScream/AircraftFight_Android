package com.example.aircraftfight_android.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;

import com.example.aircraftfight_android.game.application.ImageManager;

import java.util.HashMap;

public class DrawHeroHelper {

    private final int timeInterval;
    private final int pagesNum;
    private final Canvas canvas;
    private Bitmap hero;
    private final HashMap<Integer, Bitmap> heroHash = new HashMap<Integer, Bitmap>();

    private Context context;

    public DrawHeroHelper(int t, Canvas canvas, int multi, Context context){
        this.timeInterval = t*multi;
        this.canvas = canvas;
        this.pagesNum = 5;
        this.context = context;
        initHash();
    }

    public DrawHeroHelper(int t, Canvas canvas, Context context){
        this.timeInterval = t*2;
        this.canvas = canvas;
        this.pagesNum = 5;
        this.context = context;
        initHash();
    }

    public void drawHero(int time,int x,int y){
        int index = (time/timeInterval) % pagesNum;
        hero = heroHash.get(index);
        x = x - ImageManager.REIMU_1.getHeight()/2;
        y = y - ImageManager.REIMU_1.getWidth()/2;
        canvas.drawBitmap(hero, x, y, null);
    }

    private void initHash(){
//        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(context,)
        heroHash.put(0,ImageManager.REIMU_1);
        heroHash.put(1,ImageManager.REIMU_2);
        heroHash.put(2,ImageManager.REIMU_3);
        heroHash.put(3,ImageManager.REIMU_4);
        heroHash.put(4,ImageManager.REIMU_5);
    }

}
