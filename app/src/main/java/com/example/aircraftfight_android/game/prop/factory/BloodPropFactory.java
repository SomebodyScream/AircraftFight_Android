package com.example.aircraftfight_android.game.prop.factory;

import com.example.aircraftfight_android.game.prop.AbstractProp;
import com.example.aircraftfight_android.game.prop.BloodProp;

/**
 * 构造回血道具的工厂
 * @author 200111517
 */
public class BloodPropFactory implements PropFacctory
{
    @Override
    public AbstractProp createProp(int locationX, int locationY)
    {
        int speedX = 0;
        int speedY = 12;
        int hp = 30;
        return new BloodProp(locationX, locationY, speedX, speedY, hp);
    }
}
