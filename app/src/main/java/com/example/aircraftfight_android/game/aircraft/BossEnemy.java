package com.example.aircraftfight_android.game.aircraft;

import com.example.aircraftfight_android.game.aircraft.shoot_strategy.ShootingStrategy;


/**
 * boss机
 * @author 200111517
 */
public class BossEnemy extends AbstractEnemy
{
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int power,
                     int shootNum, ShootingStrategy shootStrategy, int score, int maxPropAriseNum, double propAriseProbability) {
        super(locationX, locationY, speedX, speedY, hp, power, shootNum, shootStrategy, score, maxPropAriseNum, propAriseProbability);
    }

    @Override
    public void explode() {
        decreaseHp(20);
    }
}
