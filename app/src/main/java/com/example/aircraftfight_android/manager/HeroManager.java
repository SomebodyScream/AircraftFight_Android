package com.example.aircraftfight_android.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.game.application.ImageManager;
import com.example.aircraftfight_android.helper.SharedPreferenceHelper;

import java.util.HashMap;

public class HeroManager {

    private int selfId;
    private static final String SP_DATABASE_HERO = "heroManage";
    private static final String SP_LABEL_SELF_ID = "selfID";
    private static final String SP_LABEL_INFO = "info";

    public static final int HERO_ID_REIMU = 1;
    public static final int HERO_ID_MARISA = 2;
    public static final int HERO_ID_YOUMU = 3;

    private final HashMap<Integer, Bitmap> heroHash = new HashMap<>();

    @SuppressLint("StaticFieldLeak")
    private static HeroManager heroManager;
    private final Context context;

    private HeroManager(Context context) {
        this.context = context;
        setSelfId(context);
        setInfo();
        setHash();
    }

    public static synchronized HeroManager getHeroManager(Context context){
        if (heroManager==null){
            heroManager = new HeroManager(context);
        }
        return heroManager;
    }

    private void setSelfId(Context context){
        SharedPreferenceHelper spHelper = new SharedPreferenceHelper(context,SP_DATABASE_HERO);
        selfId = (int)spHelper.readProperty(SP_LABEL_SELF_ID,SharedPreferenceHelper.READ_MODE_INT);
        if (selfId == SharedPreferenceHelper.DEFAULT_INT){
            spHelper.writeProperty(SP_LABEL_SELF_ID,1);
        }
    }

    private void setHash(){
        switch (selfId){
            case (1):{
                heroHash.put(0, ImageManager.REIMU_1);
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

    public int drawHeroGif(){
        int fileInt = R.drawable.gif_reimu;
        switch (selfId){
            case (1):{
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

    public int drawHeroCg(){
        int fileInt = R.drawable.cg_reimu;
        switch (selfId){
            case (1):{
                break;
            }
            case (2):{
                fileInt = R.drawable.cg_marisa;
                break;
            }
            case (3):{
                fileInt = R.drawable.cg_youmu;
                break;
            }
        }
        return fileInt;
    }

    private void setInfo(){
        SharedPreferenceHelper spHelper = new SharedPreferenceHelper(context,SP_DATABASE_HERO);
        spHelper.writeProperty("info1","Unfortunately for you, I don't particularly mind needless killing. And of course, I'm not a Youkai shrine maiden either. I'm a human shrine maiden. And I exterminate Youkai on sight.");
        spHelper.writeProperty("info2","It ain't magic if it ain't flashy. Danmaku's all about firepower");
        spHelper.writeProperty("info3","As a gardener in the service of the Saigyouji family and the bodyguard of Yuyuko Saigyouji, Youmu is a swordswoman of a two-sword school of swordplay. According to the Saigyouji family, Youmu is the second generation to take her position, the former generation being Youki Konpaku. Youki was Youmu's swordplay instructor. Youmu is also Yuyuko's sword instructor, but she is fundamentally treated as a gardener");
    }

    public String getHeroInfo(){
        SharedPreferenceHelper spHelper = new SharedPreferenceHelper(context,SP_DATABASE_HERO);
        String spLabel = SP_LABEL_INFO+selfId;
        return (String) spHelper.readProperty(spLabel,SharedPreferenceHelper.READ_MODE_STRING);
    }

    public int getSelfId(){
        return selfId;
    }

    public void setSelfId(int id){
        SharedPreferenceHelper spHelper = new SharedPreferenceHelper(context,SP_DATABASE_HERO);
        spHelper.writeProperty(SP_LABEL_SELF_ID,id);
        selfId = (int)spHelper.readProperty(SP_LABEL_SELF_ID,SharedPreferenceHelper.READ_MODE_INT);
        if (selfId == SharedPreferenceHelper.DEFAULT_INT){
            spHelper.writeProperty(SP_LABEL_SELF_ID,1);
        }
        setHash();
    }

    public void drawHero(int time,int timeInterval,int x,int y,Canvas canvas){
        int index = (time/timeInterval) % 5;
        Bitmap hero = heroHash.get(index);
        x = x - ImageManager.REIMU_1.getWidth()/2;
        y = y - ImageManager.REIMU_1.getHeight()/2;
        canvas.drawBitmap(hero, x, y, null);
    }
}
