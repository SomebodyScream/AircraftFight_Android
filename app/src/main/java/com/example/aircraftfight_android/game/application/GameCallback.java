package com.example.aircraftfight_android.game.application;

public interface GameCallback {
    void onGameOver(int score, String gameMode);

    void onScoreChanged(int score);

    void onLifeChanged(int hp);

    void onOpponentScoreChanged(int score);
}