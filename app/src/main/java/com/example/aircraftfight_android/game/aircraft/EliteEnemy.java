package com.example.aircraftfight_android.game.aircraft;


import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.aircraft.shoot_strategy.ShootingStrategy;

/**
 * 精英敌机
 * @author 200111517
 */
public class EliteEnemy extends AbstractEnemy
{
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int power,
                      int shootNum, ShootingStrategy shootStrategy, int score, int maxPropAriseNum, double propAriseProbability) {
        super(locationX, locationY, speedX, speedY, hp, power, shootNum, shootStrategy, score, maxPropAriseNum, propAriseProbability);
    }

    @Override
    public void forward()
    {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainActivity.HEIGHT ) {
            vanish();
        }
    }
}
