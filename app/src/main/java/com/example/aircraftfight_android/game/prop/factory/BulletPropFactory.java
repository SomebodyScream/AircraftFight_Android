package com.example.aircraftfight_android.game.prop.factory;

import com.example.aircraftfight_android.game.prop.AbstractProp;
import com.example.aircraftfight_android.game.prop.BulletProp;

/**
 * 构造火力道具的工厂
 * @author 200111517
 */
public class BulletPropFactory implements PropFacctory
{
    @Override
    public AbstractProp createProp(int locationX, int locationY)
    {
        int speedX = 0;
        int speedY = 12;
        return new BulletProp(locationX, locationY, speedX, speedY);
    }
}
