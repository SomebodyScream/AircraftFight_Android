package com.example.aircraftfight_android.game.prop;

import com.example.aircraftfight_android.game.aircraft.HeroAircraft;
import com.example.aircraftfight_android.game.aircraft.shoot_strategy.DirectShoot;
import com.example.aircraftfight_android.game.aircraft.shoot_strategy.ScatteringShoot;
import com.example.aircraftfight_android.game.aircraft.shoot_strategy.ShootingStrategy;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 火力道具
 * @author 200111517
 */
public class BulletProp extends AbstractProp
{
    private static ReentrantLock threadLock = new ReentrantLock();

    public BulletProp(int locationX, int locationY, int speedX, int speedY)
    {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate()
    {
        Runnable activate = () -> {
            threadLock.lock();
            try
            {
                HeroAircraft heroAircraft = HeroAircraft.getInstance();

                heroAircraft.setShootNum(3);
                ShootingStrategy shootStrategy = new ScatteringShoot();
                heroAircraft.setShootStrategy(shootStrategy);

                Thread.sleep(7000);

                heroAircraft.setShootNum(1);
                shootStrategy = new DirectShoot();
                heroAircraft.setShootStrategy(shootStrategy);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                threadLock.unlock();
            }
        };

        new Thread(activate).start();
    }
}
