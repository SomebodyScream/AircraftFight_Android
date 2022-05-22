package com.example.aircraftfight_android.game.prop;
import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.application.MusicThread;
import com.example.aircraftfight_android.game.basic.AbstractFlyingObject;

/**
 * 所有道具的抽象父类
 * @author 200111517
 */
public abstract class AbstractProp extends AbstractFlyingObject
{
    public AbstractProp(int locationX, int locationY, int speedX, int speedY)
    {
        super(locationX, locationY, speedX, speedY);
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

    /**
     * 道具生效
     */
    public abstract void activate();
}
