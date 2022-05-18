package com.example.aircraftfight_android.game.bullet;

import com.example.aircraftfight_android.game.prop.BombTarget;

/**
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements BombTarget
{

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void explode() {
        vanish();
    }
}
