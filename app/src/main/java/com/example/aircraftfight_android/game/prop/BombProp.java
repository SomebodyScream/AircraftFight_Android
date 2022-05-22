package com.example.aircraftfight_android.game.prop;

import com.example.aircraftfight_android.activity.MainActivity;
import com.example.aircraftfight_android.game.application.MusicThread;

import java.util.LinkedList;
import java.util.List;

/**
 * 炸弹道具
 * @author 200111517
 */
public class BombProp extends AbstractProp
{
    private final List<BombTarget> bombTargetList = new LinkedList<>();

    public BombProp(int locationX, int locationY, int speedX, int speedY)
    {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate()
    {
        MainActivity.musicHelper.playHeroBomb();
        bombActivate();
    }

    public void addTarget(BombTarget target) {
        bombTargetList.add(target);
    }

    public void removeTarget(BombTarget target){
        bombTargetList.remove(target);
    }

    public void bombActivate(){
        for(BombTarget target: bombTargetList) {
            target.explode();
        }
    }
}