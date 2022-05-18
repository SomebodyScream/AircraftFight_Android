package com.example.aircraftfight_android.game.aircraft.shoot_strategy;


import com.example.aircraftfight_android.game.aircraft.AbstractAircraft;
import com.example.aircraftfight_android.game.aircraft.HeroAircraft;
import com.example.aircraftfight_android.game.bullet.BaseBullet;
import com.example.aircraftfight_android.game.bullet.EnemyBullet;
import com.example.aircraftfight_android.game.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 散射策略
 * @author 200111517
 */
public class ScatteringShoot implements ShootingStrategy
{
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft)
    {
        List<BaseBullet> res = new LinkedList<>();

        String aircraftType = aircraft.getClass().getName();

        int power = aircraft.getPower();
        int shootNum = aircraft.getShootNum();
        int direction = aircraft.getDirection();

        int locationX = aircraft.getLocationX();
        int locationY = aircraft.getLocationY() + direction * 2;

        int speedY = aircraft.getSpeedY() + direction * 8;

        BaseBullet bullet;
        for(int i=0; i<shootNum; i++)
        {
            int speedX = i * 2 - shootNum + 1;
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
