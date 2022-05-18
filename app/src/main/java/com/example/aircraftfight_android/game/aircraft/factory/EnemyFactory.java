package com.example.aircraftfight_android.game.aircraft.factory;

import com.example.aircraftfight_android.game.aircraft.AbstractEnemy;

/**
 * 敌机工厂的接口
 * @author 200111517
 */
public interface EnemyFactory
{
    /**
     * 构造具体敌机的方法
     * @param hp 敌机血量
     * @param power 敌机子弹威力
     * @param propAriseProbability 敌机掉落道具概率
     * @return 任意AbstractEnemy类实例
     */
    AbstractEnemy createEnemy(int hp, int power, double propAriseProbability);
}
