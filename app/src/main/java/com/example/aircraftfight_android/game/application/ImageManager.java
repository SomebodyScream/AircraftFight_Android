package com.example.aircraftfight_android.game.application;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.aircraftfight_android.R;
import com.example.aircraftfight_android.game.aircraft.BossEnemy;
import com.example.aircraftfight_android.game.aircraft.EliteEnemy;
import com.example.aircraftfight_android.game.aircraft.HeroAircraft;
import com.example.aircraftfight_android.game.aircraft.MobEnemy;
import com.example.aircraftfight_android.game.bullet.EnemyBullet;
import com.example.aircraftfight_android.game.bullet.HeroBullet;
import com.example.aircraftfight_android.game.prop.BloodProp;
import com.example.aircraftfight_android.game.prop.BombProp;
import com.example.aircraftfight_android.game.prop.BulletProp;

import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, Bitmap> CLASSNAME_IMAGE_MAP = new HashMap<>();
//    private static Resources resources;

    public static Bitmap BACKGROUND_IMAGE;
    public static Bitmap BACKGROUND_IMAGE_2;
    public static Bitmap BACKGROUND_IMAGE_3;
    public static Bitmap BACKGROUND_IMAGE_4;
    public static Bitmap BACKGROUND_IMAGE_5;
    public static Bitmap HERO_IMAGE;
    public static Bitmap HERO_BULLET_IMAGE;
    public static Bitmap ENEMY_BULLET_IMAGE;
    public static Bitmap MOB_ENEMY_IMAGE;
    public static Bitmap ELITE_ENEMY_IMAGE;
    public static Bitmap BOSS_ENEMY_IMAGE;
    public static Bitmap BULLET_PROP_IMAGE;
    public static Bitmap BOMB_PROP_IMAGE;
    public static Bitmap BLOOD_PROP_IMAGE;
    public static Bitmap REIMU_1;
    public static Bitmap REIMU_2;
    public static Bitmap REIMU_3;
    public static Bitmap REIMU_4;
    public static Bitmap REIMU_5;

    public static void initial(Resources resources)
    {
//        ImageManager.resources = resources;
        BACKGROUND_IMAGE = BitmapFactory.decodeResource(resources, R.drawable.bg);
        BACKGROUND_IMAGE_2 = BitmapFactory.decodeResource(resources, R.drawable.bg2);
        BACKGROUND_IMAGE_3 = BitmapFactory.decodeResource(resources, R.drawable.bg3);
        BACKGROUND_IMAGE_4 = BitmapFactory.decodeResource(resources, R.drawable.bg4);
        BACKGROUND_IMAGE_5 = BitmapFactory.decodeResource(resources, R.drawable.bg5);

        HERO_IMAGE = BitmapFactory.decodeResource(resources, R.drawable.hero);
        MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(resources, R.drawable.mob);
        HERO_BULLET_IMAGE = BitmapFactory.decodeResource(resources, R.drawable.bullet_hero);
        ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(resources, R.drawable.bullet_enemy);
        ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(resources, R.drawable.elite);
        BOSS_ENEMY_IMAGE = BitmapFactory.decodeResource(resources, R.drawable.boss);
        BULLET_PROP_IMAGE = BitmapFactory.decodeResource(resources, R.drawable.prop_bullet);
        BOMB_PROP_IMAGE = BitmapFactory.decodeResource(resources, R.drawable.prop_bomb);
        BLOOD_PROP_IMAGE = BitmapFactory.decodeResource(resources, R.drawable.prop_blood);
        REIMU_1 = BitmapFactory.decodeResource(resources, R.drawable.reimu_01);
        REIMU_2 = BitmapFactory.decodeResource(resources, R.drawable.reimu_02);
        REIMU_3 = BitmapFactory.decodeResource(resources, R.drawable.reimu_03);
        REIMU_4 = BitmapFactory.decodeResource(resources, R.drawable.reimu_04);
        REIMU_5 = BitmapFactory.decodeResource(resources, R.drawable.reimu_05);

        CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
        CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BulletProp.class.getName(), BULLET_PROP_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BombProp.class.getName(), BOMB_PROP_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BloodProp.class.getName(), BLOOD_PROP_IMAGE);
    }

    public static Bitmap get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static Bitmap get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }
}