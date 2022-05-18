package com.example.aircraftfight_android.game.aircraft.shoot_strategy;

import com.example.aircraftfight_android.game.aircraft.AbstractAircraft;
import com.example.aircraftfight_android.game.aircraft.HeroAircraft;
import com.example.aircraftfight_android.game.bullet.BaseBullet;
import com.example.aircraftfight_android.game.bullet.EnemyBullet;
import com.example.aircraftfight_android.game.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 锥形射击策略，射击效果类似下图
 *       #
 *    #  #  #
 *  #    #    #
 *    #  #  #
 *       #
 * @author 200111517
 */
public class ConicalShoot implements ShootingStrategy
{
    /**
     * 子弹扩散宽度的倍数
     */
    private int k = 0;

    /**
     * 用于控制形成锥形的循环变量
     */
    private int d = 1;

    /**
     * 最大扩散宽度，即k的最大值
     */
    private int maxK = 3;

    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft)
    {
        k += d;
        if(k == maxK || k == 0){
            d = -d;
        }

        List<BaseBullet> res = new LinkedList<>();

        String aircraftType = aircraft.getClass().getName();

        int power = aircraft.getPower();
        int shootNum = aircraft.getShootNum();
        int direction = aircraft.getDirection();

        // 子弹发射位置相对飞机位置向前偏移
        int x = aircraft.getLocationX();
        int locationY = aircraft.getLocationY() + direction * 2;

        int speedX = 0;
        int speedY = aircraft.getSpeedY() + direction * 8;

        BaseBullet bullet;
        for(int i=0; i<shootNum; i++)
        {
            int locationX = x + (i * 2 - shootNum + 1) * k * 12;
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
