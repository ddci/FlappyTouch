package com.example.danielnikulin.flappy;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by danil on 19.04.2017.
 */

public class MainThread extends  Thread {

    public static final int MAX_FPS = 60;
    static public double averageFPS;
    private SurfaceHolder surfaceHolder;
    private Panel panel;
    private boolean running;
    public static Canvas canvas;

    public  void setRunning(boolean running) {
        this.running =running;
    }
    public MainThread(SurfaceHolder surfaceHolder, Panel panel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.panel = panel;

    }

    @Override
    public void run() {

        long startTime;
        long timeMilis = 1000 / MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (running){
            startTime = System.nanoTime();
            canvas = null;
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.panel.update();
                    this.panel.draw(canvas);
                }

            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){e.printStackTrace();}
                }
            }
            timeMilis = (System.nanoTime()- startTime)/1000000;
            waitTime = targetTime-timeMilis;
            try {
                if(waitTime>0)
                    this.sleep(waitTime);
                    //System.gc();

            }catch (Exception e){e.printStackTrace();}
            totalTime+= System.nanoTime()-startTime;
            frameCount++;
            //if(frameCount == MAX_FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime =0;
                System.out.println(averageFPS);
            //}


        }
    }
}