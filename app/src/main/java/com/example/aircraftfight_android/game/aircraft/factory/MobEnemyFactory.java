package com.example.aircraftfight_android.game.aircraft.factory;

import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.aircraft.AbstractEnemy;
import com.example.aircraftfight_android.game.aircraft.MobEnemy;
import com.example.aircraftfight_android.game.application.ImageManager;

/**
 * 构造普通敌机的工厂
 * @author 200111517
 */
public class MobEnemyFactory implements EnemyFactory
{
    @Override
    public AbstractEnemy createEnemy(int hp, int power, double propAriseProbability)
    {
        int locationX = (int) (Math.random() * (MainActivity.WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()));
        int locationY = (int) (Math.random() * MainActivity.HEIGHT * 0.2);

        int speedX = 0;
        int speedY = 20;

        int shootNum = 0;
        int score = 10;

        return new MobEnemy(locationX, locationY, speedX, speedY, hp, power, shootNum, score, 0, propAriseProbability);
    }
}
