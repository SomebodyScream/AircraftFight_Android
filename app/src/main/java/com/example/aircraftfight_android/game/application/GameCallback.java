package com.example.aircraftfight_android.game.application;

public interface GameCallback {
    void onGameOver(int score, String difficulty);

    void onScoreChanged(int score);

    void onLifeChanged(int hp);
}