package com.example.aircraftfight_android.game.application;

import android.content.Context;

import com.example.aircraftfight_android.game.aircraft.AbstractEnemy;
import com.example.aircraftfight_android.game.aircraft.factory.BossEnemyFactory;
import com.example.aircraftfight_android.game.aircraft.factory.EliteEnemyFactory;
import com.example.aircraftfight_android.game.aircraft.factory.MobEnemyFactory;

public class HardGame extends Game
{
    private double curEnhancement = 1.0;
    private int bossCount = 0;

    public HardGame(Context context)
    {
        super(context);
        backgroundImage = ImageManager.BACKGROUND_IMAGE_5;
        mode = Game.HARD;

        eliteEnemyAriseProb = 0.25;
        enemyMaxNumber = 6;
        bossScoreThreshold = 350;
        enemyCycleDuration = 570;
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
                AbstractEnemy eliteEnemy = new EliteEnemyFactory().createEnemy(20, (int)(30 * curEnhancement), 0.25);
                enemyAircrafts.add(eliteEnemy);
            }
            else
            {
                // 生成普通敌机
                AbstractEnemy mobEnemy = new MobEnemyFactory().createEnemy(20, 0, 0);
                enemyAircrafts.add(mobEnemy);
            }
        }
    }

    @Override
    protected void createBoss()
    {
        super.createBoss();
        enemyAircrafts.add(new BossEnemyFactory().createEnemy(150 + bossCount * 10, (int)(45 * curEnhancement), 0.9));
        bossCount++;
    }

    @Override
    protected void increaseDifficultyByTime()
    {
        if(time % (timeInterval * 500) == 0)
        {
            curEnhancement *= 1.06;
            System.out.printf("难度提升! 敌机攻击力提升倍率: %.2f", curEnhancement);

            if(time % (timeInterval * 1000) == 0)
            {
                eliteEnemyAriseProb += 0.015;
                System.out.printf(" 精英机概率: %.2f", eliteEnemyAriseProb);
            }

            if(enemyCycleDuration > 400)
            {
                enemyCycleDuration -= 15;
                System.out.print(" 敌机产生周期: " + enemyCycleDuration + "ms");
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
