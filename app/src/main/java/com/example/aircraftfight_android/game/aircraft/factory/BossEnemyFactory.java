package com.example.aircraftfight_android.game.aircraft.factory;

import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.aircraft.AbstractEnemy;
import com.example.aircraftfight_android.game.aircraft.BossEnemy;
import com.example.aircraftfight_android.game.aircraft.shoot_strategy.ScatteringShoot;
import com.example.aircraftfight_android.game.aircraft.shoot_strategy.ShootingStrategy;
import com.example.aircraftfight_android.game.application.ImageManager;

import java.util.Random;

/**
 * 构造boss机的工厂
 * @author 200111517
 */
public class BossEnemyFactory implements EnemyFactory
{
    @Override
    public AbstractEnemy createEnemy(int hp, int power, double propAriseProbability)
    {
        int locationX = (int) (Math.random() * (MainActivity.WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth()));
        int locationY = (int) (Math.random() * MainActivity.HEIGHT * 0.2);

        Random r = new Random();

        // boss机横向移动速度在 [4, 12) 中随机
        int speedX = r.nextInt(8) + 4;
        int speedY = 0;
        int shootNum = 3;
        int score = 40;

        ShootingStrategy shootStrategy = new ScatteringShoot();

        return new BossEnemy(locationX, locationY, speedX, speedY, hp,
                power, shootNum, shootStrategy, score, 2, propAriseProbability);
    }
}
