package com.example.aircraftfight_android.helper;

import java.io.Serializable;
import java.util.Date;

/**
 * 得分记录
 * @author 200111517
 */
public class SingleRecord implements Serializable
{
    private String playerName;
    private int score;
    private Date date;

    public SingleRecord(String playerName, int score, Date date)
    {
        this.playerName = playerName;
        this.score = score;
        this.date = date;
    }

    @Override
    public String toString(){
        return "player: " + playerName + ", score: " + score + ", date: " + date.toString();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
