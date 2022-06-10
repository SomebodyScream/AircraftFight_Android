package com.example.aircraftfight_android.game.application;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.aircraftfight_android.game.aircraft.AbstractEnemy;
import com.example.aircraftfight_android.game.aircraft.factory.EliteEnemyFactory;
import com.example.aircraftfight_android.game.aircraft.factory.MobEnemyFactory;
import com.example.aircraftfight_android.helper.HttpHelper;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class OnlineGame extends Game implements okhttp3.Callback
{
    private String playerId;

    public OnlineGame(Context context, GameCallback callback, String playerId)
    {
        super(context, callback);
        this.playerId = playerId;

        backgroundImage = ImageManager.BACKGROUND_IMAGE;
        mode = Game.ONLINE;

        eliteEnemyAriseProb = 0.15;
        enemyMaxNumber = 4;
        bossScoreThreshold = 450;
        enemyAriseCycleDuration = 650;
    }

    @Override
    protected void createEnemy()
    {
        // TODO

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
    protected void increaseDifficultyByTime() {

    }

    @Override
    protected void syncDataWithServer()
    {
        String url = HttpHelper.IP + "/versus?" + "playerId=" + playerId + "&score=" + score + "&gameover=false";
        HttpHelper.sendGetRequest(url, this);
    }

    @Override
    protected void gameOverCheck()
    {
        super.gameOverCheck();
        if(heroAircraft.getHp() <= 0)
        {
            String url = HttpHelper.IP + "/versus?" + "playerId=" + playerId + "&score=" + score + "&gameover=true";
            HttpHelper.sendGetRequest(url, this);
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {

    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
    {
        String responseJson = response.body().string();

        int opponentScore = -1;
        try{
            JSONObject jsonObject = new JSONObject(responseJson);
            opponentScore = jsonObject.getInt( "score" );
        }
        catch(Exception e){
            Log.d( "GameActivity", e.toString()) ;
            e.printStackTrace();
        }

        callback.onOpponentScoreChanged(opponentScore);
    }
}
