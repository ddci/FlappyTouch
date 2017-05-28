package com.example.danielnikulin.flappy;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by danielnikulin on 17.05.17.
 */

public interface Scene {

    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);

}
