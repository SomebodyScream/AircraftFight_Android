package com.example.aircraftfight_android.game.application;

import android.content.Context;

import com.example.aircraftfight_android.game.aircraft.AbstractEnemy;
import com.example.aircraftfight_android.game.aircraft.factory.BossEnemyFactory;
import com.example.aircraftfight_android.game.aircraft.factory.EliteEnemyFactory;
import com.example.aircraftfight_android.game.aircraft.factory.MobEnemyFactory;

public class NormalGame extends Game
{
    private double curEnhancement = 1.0;

    public NormalGame(Context context, GameCallback callback)
    {
        super(context, callback);
        backgroundImage = ImageManager.BACKGROUND_IMAGE_3;
        mode = Game.NORMAL;

        eliteEnemyAriseProb = 0.2;
        enemyMaxNumber = 5;
        bossScoreThreshold = 450;
        enemyAriseCycleDuration = 600;
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
                AbstractEnemy eliteEnemy = new EliteEnemyFactory().createEnemy(20, (int)(25 * curEnhancement), 0.4);
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
    protected void createBoss()
    {
        super.createBoss();
        enemyAircrafts.add(new BossEnemyFactory().createEnemy(90, (int)(35 * curEnhancement), 0.95));
    }

    @Override
    protected void increaseDifficultyByTime()
    {
        if(time % (timeInterval * 500) == 0)
        {
            curEnhancement *= 1.04;
            System.out.printf("难度提升! 敌机攻击力提升倍率: %.2f", curEnhancement);

            if(time % (timeInterval * 1000) == 0)
            {
                eliteEnemyAriseProb += 0.01;
                System.out.printf(" 精英机概率: %.2f", eliteEnemyAriseProb);
            }

            if(enemyAriseCycleDuration > 400)
            {
                enemyAriseCycleDuration -= 12;
                System.out.print(" 敌机产生周期: " + enemyAriseCycleDuration + "ms");
            }

            if(time % (timeInterval * 1500) == 0)
            {
                enemyMaxNumber++;
                System.out.print(" 最大敌机数量: " + enemyMaxNumber);
            }

            System.out.print("\n");
        }
    }
}