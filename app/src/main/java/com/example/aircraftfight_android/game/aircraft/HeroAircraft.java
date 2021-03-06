package com.example.aircraftfight_android.game.aircraft;

import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.aircraft.shoot_strategy.DirectShoot;
import com.example.aircraftfight_android.game.application.ImageManager;
import com.example.aircraftfight_android.game.bullet.BaseBullet;

import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft
{
    private static HeroAircraft heroAircraft = new HeroAircraft(
            MainActivity.WIDTH / 2,
            MainActivity.HEIGHT - ImageManager.HERO_IMAGE.getHeight() / 2,
            0, 0, 300, 10, 1
    );

    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int power, int shootNum)
    {
        super(locationX, locationY, speedX, speedY, hp, power, shootNum, -1, new DirectShoot());
    }

    public static HeroAircraft getInstance(){
        return heroAircraft;
    }

    public void initHeroAircraft()
    {
        heroAircraft.setLocation(MainActivity.WIDTH / 2,
                MainActivity.HEIGHT - ImageManager.HERO_IMAGE.getHeight() / 2);
        heroAircraft.increaseHp(maxHp);
        heroAircraft.setShootNum(1);
        heroAircraft.setShootStrategy(new DirectShoot());
        heroAircraft.isValid = true;
    }

    @Override
    public List<BaseBullet> shoot()
    {
        MainActivity.musicHelper.playHeroBulletShoot();
        return super.shoot();
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public void increaseHp(int amount)
    {
        this.hp = Math.min(this.hp + amount, this.maxHp);
    }
}
