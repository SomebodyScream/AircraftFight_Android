package com.example.aircraftfight_android.game.aircraft.shoot_strategy;

import com.example.aircraftfight_android.game.aircraft.AbstractAircraft;
import com.example.aircraftfight_android.game.aircraft.HeroAircraft;
import com.example.aircraftfight_android.game.bullet.BaseBullet;
import com.example.aircraftfight_android.game.bullet.EnemyBullet;
import com.example.aircraftfight_android.game.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 直射策略
 * @author 200111517
 */
public class DirectShoot implements ShootingStrategy
{
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft)
    {
        List<BaseBullet> res = new LinkedList<>();

        String aircraftType = aircraft.getClass().getName();

        int power = aircraft.getPower();
        int shootNum = aircraft.getShootNum();
        int direction = aircraft.getDirection();

        int x = aircraft.getLocationX();

        // 子弹发射位置相对飞机位置向前偏移
        int locationY = aircraft.getLocationY() + direction * 2;

        // 直射横向速度为0
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + direction * 8;

        BaseBullet bullet;
        for(int i=0; i<shootNum; i++)
        {
            // 多个子弹横向分散
            int locationX = x + (i * 2 - shootNum + 1) * 10;
            if(aircraftType.equals(HeroAircraft.class.getName())){
                bullet = new HeroBullet(locationX, locationY, speedX, speedY, power);
            }
            else{
                bullet = new EnemyBullet(locationX, locationY, speedX, speedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}
