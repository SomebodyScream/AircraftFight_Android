package com.example.aircraftfight_android.game.aircraft;

import com.example.aircraftfight_android.game.aircraft.shoot_strategy.ShootingStrategy;
import com.example.aircraftfight_android.game.basic.AbstractFlyingObject;
import com.example.aircraftfight_android.game.bullet.BaseBullet;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    /**
     * 子弹一次发射数量
     */
    protected int shootNum;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    protected int direction;

    /**
     * 子弹伤害
     */
    protected int power;

    /**
     * 攻击模式
     */
    protected ShootingStrategy shootStrategy;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY,
                            int hp, int power, int shootNum, int direction, ShootingStrategy shootStrategy)
    {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.power = power;
        this.shootNum = shootNum;
        this.direction = direction;
        this.shootStrategy = shootStrategy;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp(){
        return hp;
    }

    public int getPower(){
        return power;
    }

    public int getDirection(){
        return direction;
    }

    public int getShootNum(){
        return shootNum;
    }

    public void setShootNum(int shootNum)
    {
        this.shootNum = shootNum;
    }

    public ShootingStrategy getShootStrategy(){
        return shootStrategy;
    }

    public void setShootStrategy(ShootingStrategy shootStrategy){
        this.shootStrategy = shootStrategy;
    }


    /**
     * 飞机射击方法
     * @return
     *  可射击对象返回子弹Lists
     *  非可射击对象返回空List
     */
    public List<BaseBullet> shoot()
    {
        return shootStrategy.shoot(this);
    }
}


