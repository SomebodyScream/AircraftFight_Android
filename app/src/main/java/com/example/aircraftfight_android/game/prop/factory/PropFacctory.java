package com.example.aircraftfight_android.game.prop.factory;

import com.example.aircraftfight_android.game.prop.AbstractProp;

/**
 * 道具工厂的接口
 * @author 200111517
 */
public interface PropFacctory
{
    /**
     * 构造具体道具的方法
     * @param locationX 道具产生的水平坐标
     * @param locationY 道具产生的垂直坐标
     * @return 任意AbstractProp类实例
     */
    AbstractProp createProp(int locationX, int locationY);
}
