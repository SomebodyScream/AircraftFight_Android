package com.example.aircraftfight_android.game.aircraft;

import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractEnemy {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int power,
                    int shootNum, int score, int maxPropAriseNum, double propAriseProbability) {
        super(locationX, locationY, speedX, speedY, hp, power, shootNum, null, score, maxPropAriseNum, propAriseProbability);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainActivity.HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }
}
