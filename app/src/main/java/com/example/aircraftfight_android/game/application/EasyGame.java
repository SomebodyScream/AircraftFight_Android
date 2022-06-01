package com.example.aircraftfight_android.game.application;


import android.content.Context;

import com.example.aircraftfight_android.game.aircraft.AbstractEnemy;
import com.example.aircraftfight_android.game.aircraft.factory.EliteEnemyFactory;
import com.example.aircraftfight_android.game.aircraft.factory.MobEnemyFactory;

public class EasyGame extends Game
{
    public EasyGame(Context context, GameCallback callback)
    {
        super(context, callback);
        backgroundImage = ImageManager.BACKGROUND_IMAGE;
        mode = Game.EASY;

        eliteEnemyAriseProb = 0.15;
        enemyMaxNumber = 4;
        bossScoreThreshold = 10000007;
        enemyAriseCycleDuration = 650;
    }

    @Override
    protected void createEnemy()
    {
        // 当前处boss外的敌机数量
        int curEnemyNum = enemyAircrafts.size() - (isBossExist ?1 :0);

        // 新敌机产生
        if (curEnemyNum < enemyMaxNumber)
        {
            if(Math.random() < eliteEnemyAriseProb)
            {
                // 生成精英敌机
                AbstractEnemy eliteEnemy = new EliteEnemyFactory().createEnemy(10, 20, 0.5);
                enemyAircrafts.add(eliteEnemy);
            }
            else
            {
                // 生成普通敌机
                AbstractEnemy mobEnemy = new MobEnemyFactory().createEnemy(10, 0, 0);
                enemyAircrafts.add(mobEnemy);
            }
        }
    }

    @Override
    protected void createBoss() {
    }

    @Override
    protected void increaseDifficultyByTime() {
    }
}
