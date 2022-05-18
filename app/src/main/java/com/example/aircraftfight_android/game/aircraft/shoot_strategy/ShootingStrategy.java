package com.example.aircraftfight_android.game.aircraft.shoot_strategy;

import com.example.aircraftfight_android.game.aircraft.AbstractAircraft;
import com.example.aircraftfight_android.game.bullet.BaseBullet;


import java.util.List;

/**
 * 子弹策略类接口
 */
public interface ShootingStrategy
{
    /**
     * 子弹射击策略
     * @param aircraft 使用该射击策略的飞机
     * @return BaseBullet实例的List
     */
    List<BaseBullet> shoot(AbstractAircraft aircraft);
}
