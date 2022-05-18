package com.example.aircraftfight_android.game.aircraft.factory;

import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.aircraft.AbstractEnemy;
import com.example.aircraftfight_android.game.aircraft.EliteEnemy;
import com.example.aircraftfight_android.game.aircraft.shoot_strategy.DirectShoot;
import com.example.aircraftfight_android.game.aircraft.shoot_strategy.ShootingStrategy;
import com.example.aircraftfight_android.game.application.ImageManager;

import java.util.Random;


/**
 * 构造精英敌机的工厂
 * @author 200111517
 */
public class EliteEnemyFactory implements EnemyFactory
{
    @Override
    public AbstractEnemy createEnemy(int hp, int power, double propAriseProbability)
    {
        int locationX = (int) (Math.random() * (MainActivity.WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()));
        int locationY = (int) (Math.random() * MainActivity.HEIGHT * 0.2);

        Random r = new Random();

        // 精英敌机横向移动速度在 [0, 7) 中随机
        int speedX = r.nextInt(7);
        // 精英敌机纵向移动速度在 [6, 16) 中随机
        int speedY = r.nextInt(10) + 6;

        int shootNum = 1;
        int score = 20;
        ShootingStrategy shootStrategy = new DirectShoot();

        return new EliteEnemy(locationX, locationY, speedX, speedY,
                hp, power, shootNum, shootStrategy, score, 1, propAriseProbability);
    }
}
