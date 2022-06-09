package com.example.aircraftfight_android.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.aircraftfight_android.game.application.ImageManager;

import java.util.HashMap;
import com.example.aircraftfight_android.R;

@Deprecated
public class DrawHeroHelper {

    private final int timeInterval;
    private final int pagesNum;
    private final int selfId = 3;
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

    public DrawHeroHelper(Context context){
        this.timeInterval = 1;
        this.canvas = null;
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

    public int drawHeroGif(){
        int fileInt = R.drawable.gif_reimu;
        switch (selfId){
            case (1):{
                fileInt = R.drawable.gif_reimu;
                break;
            }
            case (2):{
                fileInt = R.drawable.gif_marisa;
                break;
            }
            case (3):{
                fileInt = R.drawable.gif_youmu;
                break;
            }
        }
        return fileInt;
    }

    public int drawHeroGif(int index){
        int fileInt = R.drawable.gif_reimu;
        switch (selfId){
            case (1):{
                fileInt = R.drawable.gif_reimu;
                break;
            }
            case (2):{
                fileInt = R.drawable.gif_marisa;
                break;
            }
            case (3):{
                fileInt = R.drawable.gif_youmu;
                break;
            }
        }
        return fileInt;
    }

    private void initHash(){
//        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(context,)
        switch (selfId){
            case (1):{
                heroHash.put(0,ImageManager.REIMU_1);
                heroHash.put(1,ImageManager.REIMU_2);
                heroHash.put(2,ImageManager.REIMU_3);
                heroHash.put(3,ImageManager.REIMU_4);
                heroHash.put(4,ImageManager.REIMU_5);
                break;
            }
            case (2):{
                heroHash.put(0,ImageManager.MARISA_1);
                heroHash.put(1,ImageManager.MARISA_2);
                heroHash.put(2,ImageManager.MARISA_3);
                heroHash.put(3,ImageManager.MARISA_4);
                heroHash.put(4,ImageManager.MARISA_5);
                break;
            }
            case (3):{
                heroHash.put(0,ImageManager.YOUMU_1);
                heroHash.put(1,ImageManager.YOUMU_2);
                heroHash.put(2,ImageManager.YOUMU_3);
                heroHash.put(3,ImageManager.YOUMU_4);
                heroHash.put(4,ImageManager.YOUMU_5);
                break;
            }
        }
    }

}
