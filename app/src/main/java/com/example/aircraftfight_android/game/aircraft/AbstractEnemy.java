package com.example.aircraftfight_android.game.aircraft;

import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.aircraft.shoot_strategy.ShootingStrategy;
import com.example.aircraftfight_android.game.bullet.BaseBullet;
import com.example.aircraftfight_android.game.prop.AbstractProp;
import com.example.aircraftfight_android.game.prop.BombTarget;
import com.example.aircraftfight_android.game.prop.factory.BloodPropFactory;
import com.example.aircraftfight_android.game.prop.factory.BombPropFactory;
import com.example.aircraftfight_android.game.prop.factory.BulletPropFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * 敌机的抽象父类
 * @author 200111517
 */
public abstract class AbstractEnemy extends AbstractAircraft implements BombTarget
{
    /**
     * 击毁得分
     */
    protected int score;

    /**
     * 最大道具掉落数量
     */
    protected int maxPropAriseNum;

    /**
     * 每个道具掉落概率
     */
    protected double propAriseProbability;

    public AbstractEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int power,
                         int shootNum, ShootingStrategy shootStrategy, int score, int maxPropAriseNum, double propAriseProbability)
    {
        super(locationX, locationY, speedX, speedY, hp, power, shootNum, 1, shootStrategy);
        this.score = score;
        this.maxPropAriseNum = maxPropAriseNum;
        this.propAriseProbability = propAriseProbability;
    }

    public int getScore()
    {
        return score;
    }

    @Override
    public List<BaseBullet> shoot()
    {
        MainActivity.musicHelper.playEnemyShoot();
        return super.shoot();
    }

    /**
     * 敌机掉落道具的方法
     * @return
     * 不可掉落道具的敌机返回空List
     * 可掉落道具的敌机返回AbstractProp类的List
     * */
    public List<AbstractProp> dropProp()
    {
        List<AbstractProp> res = new LinkedList<>();
        for(int i=0; i<maxPropAriseNum; i++)
        {
            // 不产生道具
            if(Math.random() > this.propAriseProbability) {
                continue;
            }

            double rnd = Math.random();
            double bulletPropThreshold = 1.0 / 3.0;
            double bombPropThreshold = 2.0 / 3.0;

            int locationX = this.locationX + (int)((Math.random() - 0.5) * this.getWidth());
            int locationY = this.locationY + (int)((Math.random() - 0.5) * this.getHeight());

            AbstractProp prop;
            if(rnd < bulletPropThreshold){
                prop = new BulletPropFactory().createProp(locationX, locationY);
            }
            else if(rnd < bombPropThreshold){
                prop = new BombPropFactory().createProp(locationX, locationY);
            }
            else{
                prop = new BloodPropFactory().createProp(locationX, locationY);
            }

            res.add(prop);
        }
        return res;
    }

    @Override
    public void explode() {
        decreaseHp(maxHp);
    }
}
