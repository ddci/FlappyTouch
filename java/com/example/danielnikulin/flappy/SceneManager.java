package com.example.danielnikulin.flappy;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import static com.example.danielnikulin.flappy.MainThread.canvas;

/**
 * Created by danielnikulin on 17.05.17.
 */

public class SceneManager {
    private ArrayList<Scene> scenes = new ArrayList<>();
    static public int ACTIVE_SCENE;

    public SceneManager(Panel view){
        ACTIVE_SCENE = 0;
        scenes.add(new GameplayScene(view));
    }

    public void reciveTouch(MotionEvent event){

    scenes.get(ACTIVE_SCENE).recieveTouch(event);

    }
    public  void update(){
        scenes.get(ACTIVE_SCENE).update();


    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);

    }
}
