package com.example.danielnikulin.flappy;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by danielnikulin on 11.05.17.
 */

public class Panel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;
    private SceneManager manager;
    // Константы и переменные для управления звуком
    public static final int BACKGROUND_SOUND_ID = 0;
    public static final int TARGET_SOUND_ID = 1;
    private SoundPool soundPool; // Воспроизведение звуков
    private SparseIntArray soundMap; // Связь идентификаторов с SoundPool




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Panel (Context context){
        super(context);
        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;
        thread = new MainThread(getHolder(),this);
        // SOUNDS

        // configure audio attributes for game audio
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        // initialize SoundPool to play the app's three sound effects
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(1);
        builder.setAudioAttributes(attrBuilder.build());
        soundPool = builder.build();

        // create Map of sounds and pre-load sounds
        soundMap = new SparseIntArray(2); // create new SparseIntArray
        soundMap.put(BACKGROUND_SOUND_ID,
                soundPool.load(context, R.raw.background, 1));
        soundMap.put(TARGET_SOUND_ID,
                soundPool.load(context, R.raw.blocker_hit, 1));
        //////END SOUNDS CODE
        manager = new SceneManager(this);
        setFocusable(true);

    }

    public void playSound(int soundId) {
        soundPool.play(soundMap.get(soundId), 1, 1, 1, 0, 1f);
    }

    @Override
    public void surfaceChanged (SurfaceHolder holder,int format,int width,int hight){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread (getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }



    @Override
    public  void surfaceDestroyed(SurfaceHolder holder ) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){e.printStackTrace();}
            retry = false;

        }

    }

    @Override

    public boolean onTouchEvent(MotionEvent event){
        manager.reciveTouch(event);

        return  true;
        //return super.onTouchEvent(event);
    }




    public void draw (Canvas canvas){
        super.draw(canvas);
        manager.draw(canvas);



    }



    public void update(){
        manager.update();

    }




}