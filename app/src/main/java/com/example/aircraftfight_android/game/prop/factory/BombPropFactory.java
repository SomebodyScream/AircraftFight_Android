package com.example.aircraftfight_android.game.prop.factory;

import com.example.aircraftfight_android.game.prop.AbstractProp;
import com.example.aircraftfight_android.game.prop.BombProp;

/**
 * 构造炸弹道具的工厂
 * @author 200111517
 */
public class BombPropFactory implements PropFacctory
{
    @Override
    public AbstractProp createProp(int locationX, int locationY)
    {
        int speedX = 0;
        int speedY = 12;
        return new BombProp(locationX, locationY, speedX, speedY);
    }
}
