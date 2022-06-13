package com.example.aircraftfight_android.mview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class BackgroundVideoView extends VideoView
{
    public BackgroundVideoView(Context context) {
        super(context);
    }

    public BackgroundVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackgroundVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
