package com.example.aircraftfight_android.game.application;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MusicThread extends Thread
{
    private String filename;
    private boolean isPlay;
    private boolean isLoop;

    public MusicThread(String filename, boolean isLoop, boolean isPlay)
    {
        this.filename = filename;
        this.isLoop = isLoop;
        this.isPlay = isPlay;
    }

    /**
     * TODO
     */
    public void stopPlaying(){
        Log.d("Music",  "stop playing" + filename);
    }

    /**
     * TODO
     */
    @Override
    public void run() {
        Log.d("Music", "start playing" + filename);
    }
}