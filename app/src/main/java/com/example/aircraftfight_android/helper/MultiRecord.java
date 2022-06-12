package com.example.aircraftfight_android.helper;

import java.util.Date;

public class MultiRecord
{
    private SingleRecord userRecord;
    private SingleRecord opponentRecord;

    public MultiRecord(String userName, String opponentName, int userScore, int opponentScore, Date date)
    {
        this.userRecord = new SingleRecord(userName, userScore, date);
        this.opponentRecord = new SingleRecord(opponentName, opponentScore, date);
    }

    @Override
    public String toString(){
        return "user name: " + userRecord.getPlayerName() + ", user score: " + userRecord.getScore() +
                "opponent name: " + opponentRecord.getPlayerName() + ", opponent score: " + opponentRecord.getScore() +
                ", date: " + userRecord.getDate().toString();
    }

    public String getUserName() {
        return userRecord.getPlayerName();
    }

    public void setUserName(String playerName) {
        userRecord.setPlayerName(playerName);
    }

    public int getUserScore(){
        return userRecord.getScore();
    }

    public void setUserScore(int score) {
        userRecord.setScore(score);
    }

    public String getOpponentName() {
        return opponentRecord.getPlayerName();
    }

    public void setOpponentName(String playerName) {
        opponentRecord.setPlayerName(playerName);
    }

    public int getOpponentScore(){
        return opponentRecord.getScore();
    }

    public void setOpponentScore(int score) {
        opponentRecord.setScore(score);
    }

    public Date getDate() {
        return userRecord.getDate();
    }

    public void setDate(Date date) {
        userRecord.setDate(date);
        opponentRecord.setDate(date);
    }
}
