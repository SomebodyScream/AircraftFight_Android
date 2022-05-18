package com.example.aircraftfight_android.game.prop;


import com.example.aircraftfight_android.game.aircraft.HeroAircraft;

/**
 * 回血道具
 * @author 200111517
 */
public class BloodProp extends AbstractProp
{
    private int healingAmount;

    public BloodProp(int locationX, int locationY, int speedX, int speedY, int healingAmount)
    {
        super(locationX, locationY, speedX, speedY);
        this.healingAmount = healingAmount;
    }

    public int getHealingAmount()
    {
        return healingAmount;
    }

    @Override
    public void activate(boolean musicOn)
    {
        super.activate(musicOn);
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        heroAircraft.increaseHp(healingAmount);
    }
}
